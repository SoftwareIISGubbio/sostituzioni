#!/bin/bash
# ATTENZIONE: questo script fa affidamento su una serie di cose più o meno standard
# - la variabile JAVA_HOME deve essere impostata
# - maven deve essere installato e sul path
# - il progetto usa maven 
# - mvn package mette tutti i jar in target/jars
# - le icone sono nella cartella icone

if [ -z "$JAVA_HOME" ]; then
    echo "la variabile JAVA_HOME non è stata impostata"
    exit 0
fi

# comando per jpackage
JPACKAGE=$JAVA_HOME/bin/jpackage
# dove stanno i jar (cartella impostata nel file pom.xml)
CARTELLA_JARS=target/jars
# cartella di lavoro
CARTELLA_LAVORO=target/lavoro
# dove mettere il file compilato
DESTINAZIONE=target
# versione (recuperata dal file pom)
VERSIONE=$(grep -m 1 version pom.xml | sed 's/[^0-9\\.]//g')
# nome del jar principale (contiene anche il numero di versione)
JAR_PRINCIPALE="sostituzioni-$VERSIONE.jar"
# nome icona, dipende dal sistema operativo
# tolgo i jar messi da maven pr fx imn modo da copiare binari e jar esattamente della stessa versione
# windows meglio lasciarlo in else
ICONA=icona/icona.icns
TIPO_PACCHETTO="dmg"
JAVA_VENDOR=$($JAVA_HOME/bin/java -XshowSettings:properties -version 2>&1 | grep 'java.vendor =' | awk -F'= ' '{print $2}')

echo "----- ambiente di lavoro -------------------------------------"
echo "JAVA_HOME   : $JAVA_HOME"
echo "JAVA_VENDOR : $JAVA_VENDOR"
echo "JPACKAGE    : $JPACKAGE"
echo "MAVEN       : $(which mvn)"
echo "OSTYPE      : $OSTYPE"
echo ""
echo "CARTELLA_JARS    : $CARTELLA_JARS"
echo "JAR_PRINCIPALE   : $JAR_PRINCIPALE"
echo "ICONA            : $ICONA"
echo "VERSIONE         : $VERSIONE"
echo ""
echo "CARTELLA_LAVORO  : $CARTELLA_LAVORO"
echo "DESTINAZIONE     : $DESTINAZIONE"
echo "TIPO_PACCHETTO   : $TIPO_PACCHETTO"
echo "--------------------------------------------------------------"

mvn -q -f pomLiberica.xml clean
mvn -q -f pomLiberica.xml package -DskipTests

mkdir $CARTELLA_LAVORO
cp $CARTELLA_JARS/* $CARTELLA_LAVORO

COMMAND="$JPACKAGE --name sostituzioni --app-version $VERSIONE --icon $ICONA --type $TIPO_PACCHETTO \
    --input $CARTELLA_LAVORO --dest $DESTINAZIONE \
    --add-modules javafx.controls,javafx.media,javafx.fxml,javafx.web,jdk.charsets \
    --main-class it.edu.iisgubbio.sostituzioni.FinestraPrincipale --main-jar $JAR_PRINCIPALE \
    --mac-package-name Sostituzioni \
    --mac-sign \
    --mac-package-identifier it.edu.iisgubbio.sostituzioni"

echo $COMMAND

$COMMAND
