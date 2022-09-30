#!/bin/bash
# ATTENZIONE: questo script fa affidamento su una serie di cose più o meno standard
# - la variabile JAVA_HOME deve essere impostata e puntare ad un pacchetto "liberica-full"
# - maven deve essere installato e sul path
# - il progetto usa maven 
# - mvn package mette tutti i jar in target/lib
# - le icone sono nella cartella icona
# - su Windows deve esserci 7zip ionstallato in "/c/Program\ Files/7-Zip/7z"

if [ -z "$JAVA_HOME" ]; then
    echo "la variabile JAVA_HOME non è stata impostata"
    exit 0
fi

# comando per jpackage
JPACKAGE=$JAVA_HOME/bin/jpackage
# dove stanno i jar (cartella impostata nel file pom.xml)
CARTELLA_JARS=target/lib
# dove mettere il file compilato
DESTINAZIONE=target
# versione (recuperata dal file pom)
VERSIONE=$(grep -m 1 version pom.xml | sed 's/[^0-9\\.]//g')
# nome del jar principale (contiene anche il numero di versione)
JAR_PRINCIPALE="sostituzioni-$VERSIONE.jar"
# nome icona, dipende dal sistema operativo

echo "----- ambiente di lavoro -------------------------------------"
echo "JAVA_HOME        : $JAVA_HOME"
echo "JPACKAGE         : $JPACKAGE"
echo "MAVEN            : $(which mvn)"
echo "OSTYPE           : $OSTYPE"
echo ""
echo "CARTELLA_JARS  : $CARTELLA_JARS"
echo "JAR_PRINCIPALE : $JAR_PRINCIPALE"
echo "VERSIONE       : $VERSIONE"
echo ""
echo "DESTINAZIONE   : $DESTINAZIONE"
echo "--------------------------------------------------------------"

mvn -q clean
mvn -q package -DskipTests

# https://stackoverflow.com/questions/394230/how-to-detect-the-os-from-a-bash-script
# windows meglio lasciarlo in else
if [[ "$OSTYPE" == "darwin"*  ]]; then
    # ============================= file firmato per macOS ======================================
    jpackage --name sostituzioni --app-version $VERSIONE --icon icona/icona.icns --type dmg \
    --input target/lib --dest target \
    --add-modules javafx.controls,javafx.media,javafx.fxml,javafx.web,javafx.swing,jdk.charsets \
    --main-class it.edu.iisgubbio.sostituzioni.FinestraPrincipale --main-jar sostituzioni-$VERSIONE.jar \
    --mac-package-name Sostituzioni \
    --mac-sign --mac-signing-key-user-name "Developer ID Application: IIS Cassata Gattapone (NMMRHK5G25)" \
    --mac-package-identifier it.edu.iisgubbio.sostituzioni
elif [[ "$OSTYPE" == "linux"* ]]; then
    echo "please insert command for Linux"
else
    # ============================= zip per Windows =============================================
    jpackage --name sostituzioni --app-version $VERSIONE --icon icona/icona.ico --type app-image \
    --input target/lib --dest target/compilato \
    --add-modules javafx.controls,javafx.media,javafx.fxml,javafx.web,javafx.swing,jdk.charsets \
    --main-class it.edu.iisgubbio.sostituzioni.FinestraPrincipale --main-jar sostituzioni-$VERSIONE.jar
    cd target/compilato
    /c/Program\ Files/7-Zip/7z a -tzip ../sostituzioni-$VERSIONE.zip *
fi
