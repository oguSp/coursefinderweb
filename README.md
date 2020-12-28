# Coursefinderweb

Versione del software che gira su server web. È stato utilizzato il framework <i>Spring</i> per la realizzazione del sistema.
È stata utilizzata la libreria <i>Lombok</i> per la gestione del codice ripetuto (metodi getter e setter,equals e hashcode,costruttori).
Il sistema preleva i dati dal file <i>csv</i> quando interrogato intolre è predisposta l'integrazione con il database <i>postgre sql</i> anche se questa
non è stata implementata. La logica di funzionamento è la stessa della versione del software a riga di comando (ho cercato di applicare al meglio il design MVC).

# Richiesta e risposta

Il backend risponde all'URL <i>/api/coursefinder/findcourse</i> con un risultato in formato <i>JSON</i> rappresentante una lista di corsi con tutte le informazioni
relative a ciascuno di essi: Codice,Descrizione,Docenti,anno corso e data di lancio.
Le opzioni sono le stesse della versione a riga di comando "mappate" in formato <i>JSON</i>. Se i docenti da ricercare sono più di uno questi
vanno separati con una virgola.

<b>ESEMPIO DI RICHIESTA</B>

```json
{
    "i": "Esther Duflo,Abhijit Banerjee",
    "l": 2013
}
```

<b>ESEMPIO DI RISPOSTA</b>

```json
[
    {
        "courseNumber": "14.73x",
        "courseTitle": "The Challenges of Global Poverty",
        "courseYear": {
            "year": 1,
            "launchDate": "2013-02-11T23:00:00.000+00:00"
        },
        "instructors": [
            {
                "name": "Esther Duflo"
            },
            {
                "name": "Abhijit Banerjee"
            }
        ],
        "institution": {
            "name": "MITx"
        }
    }
]
```
