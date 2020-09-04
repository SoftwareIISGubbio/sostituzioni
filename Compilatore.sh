clear
echo "##########################################################################################"
echo "# Auto Compilatore by Michele Vantaggi                                                   #"
echo "# Versione 1.4-IIS (Per ProfFinder)                                                      #"
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
echo "###################################### PER ESEGUIRE ######################################"
echo "Esportare il programma in un file .jar eseguibile."
echo "Spostare questo file in una cartella vuota ed eseguire in queste condizioni:"
echo "Copiare le cartelle del JDK e di javaFX si trovano nella stessa cartella di questo file"
echo "Eseguire solo in determinate condizioni:"
echo "  CONDIZIONE 1- Bisogna avere WiX 3.0 installato(Windows)"
if [ -z ${1+x} ]
then
    echo "  CONDIZIONE 2-bisogna passare il percorso (assoluto o relativo non ha importanza) del file.jar scrivendo: Compilatore.sh PERCORSO_FILE.jar"
else
    echo "  INFO-La classe principale è FinestraPrincipale, se verrà cambiata si è liberi di poter modificare questo script"
    echo "  INFO-l'icona viene presa direttamente all'interno del file .jar"
    read -p "Vuoi proseguire?(S/N): " scelta
    if [ $scelta == "S" -o $scelta == "s" ]
    then
       esegui $1 $2
    else
        exit
    fi
fi

