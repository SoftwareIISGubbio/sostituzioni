clear
echo "##########################################################################################"
echo "# Auto Compilatore by Michele Vantaggi                                                   #"
echo "# Versione 1.5-IIS (Per ProfFinder)                                                      #"
echo "# Programma che compila i file .jar eseguibili in .exe grazie a JPackage (Java 14 in su) #"
echo "##########################################################################################"
echo ""

esegui(){
    mkdir esportazione
    echo "Creata cartella esportazione"
    DATO=$1
    posizione=${DATO%/*.jar}
    nome=${DATO/$posizione"/"/}
    cp "$1" "esportazione"
    echo "Spostato il file .jar nella cartella"
    cp javafx*/lib/*.jar esportazione
    cp javafx*/bin/* esportazione
    echo "Copiati i file di JavaFX"
    echo "Estrazione dell'icona dal file .jar"
    unzip -p $1 it/edu/iisgubbio/sostituzioni/icona.ico > icona.ico
    echo "Inizio compilazione"
    jdk*/bin/jpackage --type app-image -n ${nome%.jar} --icon icona.ico --input esportazione \
        --add-modules javafx.controls,javafx.media,javafx.fxml,jdk.charsets --module-path javafx*/lib/ \ 
        --main-class it.edu.iisgubbio.sostituzioni.FinestraPrincipale --main-jar $nome
    rm -rf esportazione
    rm icon.ico
    echo "Compilazione completata"
    read -p "Premi un tasto per continuare..."
}

eseguiCartella(){
    echo ""
    echo "cartella con i jar: $1"
    echo "jar principale: $2"

    mkdir esportazione
    echo "Creata cartella esportazione"
    
    cp $1/* "esportazione"
    echo "Copiati file .jar nella cartella"
    
    cp javafx*/lib/*.jar esportazione
    cp javafx*/bin/* esportazione
    echo "Copiati i file di JavaFX"
    
    echo "Estrazione dell'icona dal file .jar"
    unzip -p $1/$2 it/edu/iisgubbio/sostituzioni/icona.ico > icona.ico
    
    echo "Inizio compilazione"
    jdk*/bin/jpackage --type app-image -n sostituzioni --icon icona.ico --input esportazione \
        --add-modules javafx.controls,javafx.media,javafx.fxml,jdk.charsets --module-path javafx*/lib/ \
        --main-class it.edu.iisgubbio.sostituzioni.FinestraPrincipale --main-jar $2
   
    rm -rf esportazione
    rm icona.ico
    echo "Compilazione completata."
}
echo "###################################### PER ESEGUIRE ######################################"
echo "- Esportare il programma in un file .jar eseguibile oppure generare una cartella con tutti i jar necessari"
echo "- Spostare questo file in una cartella che contiene JDK e JavaFX"
echo "- Se si usa Windows bisogna avere WiX 3.0 installato"
echo "- La classe principale è it.edu.iisgubbio.sostituzioni.FinestraPrincipale"
echo "- L'icona deve trovarsi nel jar principale: it/edu/iisgubbio/sostituzioni/icona.ico"
if [ -z $1 ]  # -z string True if the string is null (an empty string)
then
    echo "uso del programma"
    echo "  Compilatore.sh <percorsoJarDelProgrammaConTutteLeLibrerie>"
    echo "oppure"
    echo "  Compilatore.sh <nomeJarPrincipale> <cartellaContuttiIJar>"
else
    read -p "Vuoi proseguire?(S/N): " scelta
    if [ $scelta == "S" -o $scelta == "s" ]
    then
        if [ -z $2 ]
        then
            esegui $1
        else
            eseguiCartella $1 $2
        fi
    else
        exit
    fi
fi

