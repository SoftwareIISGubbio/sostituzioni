#!/bin/bash
# ATTENZIONE: questo script fa affidamento su una serie di cose più o meno standard
# - la variabile JAVA_HOME deve essere impostata
# - maven deve essere installato e sul path
# - il progetto usa maven 
# - mvn package mette tutti i jar in target/jars
# - le icone sono nella cartella icone

if [ "$#" -ne 2 ]; then
    echo "uso del programma:"
    echo "creazionePacchetto.sh <path_cartella_principale_javafx_sdk> <path_cartella_principale_javafx_jmods>"
    exit 0
fi
if [ -z "$JAVA_HOME" ]; then
    echo "la variabile JAVA_HOME non è stata impostata"
    exit 0
fi

# ilprimo parametro è la poszione dell'SDK di JavaFX
CARTELLA_FX_SDK=$1
CARTELLA_FX_JMODS=$2
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

echo "----- ambiente di lavoro -------------------------------------"
echo "JAVA_HOME : $JAVA_HOME"
echo "JPACKAGE  : $JPACKAGE"
echo "MAVEN     : $(which mvn)"
echo "OSTYPE    : $OSTYPE"
echo ""
echo "CARTELLA_JARS    : $CARTELLA_JARS"
echo "JAR_PRINCIPALE   : $JAR_PRINCIPALE"
echo "ICONA            : $ICONA"
echo "VERSIONE         : $VERSIONE"
echo "CARTELLA_FX_SDK  : $CARTELLA_FX_SDK"
echo "CARTELLA_FX_JMODS: $CARTELLA_FX_JMODS"
echo ""
echo "CARTELLA_LAVORO  : $CARTELLA_LAVORO"
echo "DESTINAZIONE     : $DESTINAZIONE"
echo "TIPO_PACCHETTO   : $TIPO_PACCHETTO"
echo "--------------------------------------------------------------"

mvn -q clean
mvn -q package -DskipTests

mkdir $CARTELLA_LAVORO
cp $CARTELLA_JARS/* $CARTELLA_LAVORO
rm $CARTELLA_LAVORO/javafx*

# https://stackoverflow.com/questions/394230/how-to-detect-the-os-from-a-bash-script
cp $CARTELLA_FX_SDK/lib/* $CARTELLA_LAVORO

COMMAND="$JPACKAGE --name sostituzioni --app-version $VERSIONE --icon $ICONA --type $TIPO_PACCHETTO \
    --input $CARTELLA_LAVORO --dest $DESTINAZIONE --module-path $CARTELLA_FX_JMODS \
    --add-modules javafx.controls,javafx.media,javafx.fxml,javafx.web,jdk.charsets \
    --main-class it.edu.iisgubbio.sostituzioni.FinestraPrincipale --main-jar $JAR_PRINCIPALE \
    --mac-package-name Sostituzioni \
    --mac-sign \
    --mac-package-identifier it.edu.iisgubbio.sostituzioni"

echo $COMMAND

$COMMAND


    