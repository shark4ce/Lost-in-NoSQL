# Lost-in-NoSQL
In acest program am simulat implementarea unei baze de date NoSQL, distribuita pe mai multe noduri, numita LNoSQL, ce suporta un limbaj simplu de interogare, folosinf princiipile OOP si Java.







Baza de date non-relațională numită “Lost in NoSQL” este folosită pentru a stoca eficient
date sub formă de entități ce vor fi repartizate pe mai multe noduri N. Vor putea fi stocate mai
multe tipuri de entități caracterizate de atribute, dintre care unul va fi considerat cheie primară
(după care va fi identificată unic o valoare a entității stocate în tabel).


Baza de date LNoSQL asigura si redundanta datelor, ceea ce înseamnă ca datele
vor putea fi disponibile indiferent de cate noduri sunt up la un moment dat. În acest sens vor fi
implementate următoarele funcționalități:


- Fiecare entitate va avea o caracteristica ce poate fi configurată la creare numita factor
de replicare (RF), ce va fi mai mica sau egala cu numărul de noduri N (RF <= N). Acest
factor care reprezintă numărul de copii ale instanțelor entităților pe fiecare dintre
nodurile bazei de date (de exemplu, daca avem o baza de date cu N = 5 noduri si
entitatea Produs are RF = 3 atunci orice intrare a entității Produs va trebui scrisa in 3 din
cele 5 noduri ale bazei de date).


- Un nod al bazei de date are capacitate limitata (MaxCapacity) si daca se atinge aceasta
limita scrierea se va face în următorul nod care este liber (nu și-a atins capacitatea
maximă).


- Un nod al bazei de date poate sa conțină entități de toate tipurile declarate și nu este
constrâns în a stoca un singur tip de entitate.


- Fiecare entitate din baza de date va fi caracterizată de mai multe atribute ce pot fi de
tipul String, Integer sau Float.


- Ordinea de scriere în nodurile bazei de date se va face începând cu cel mai ocupat nod.
Daca doua noduri au același grad de ocupare se va alege cel cu id-ul mai mic.


- Fiecare entitate are o cheie primara după care se va identifica unic fiecare instanță și va
fi întotdeauna primul atribut al entității la creare.


- Pentru fiecare instanță a unei entități din baza de date se va ține cont de data la care s-a
făcut ultimul update (se va folosi pentru aceasta timestamp-ul local al mașinii).

Ca orice baza de date, fie SQL sau NoSQL, va trebui sa suporte un set de comenzi pentru
crearea entităților, inserarea de instanțe ale entităților, actualizare si ștergere. Pentru baza de date
LNoSQL acestea sunt:


1) Creare Baza de date:


-Comanda prin care se creează baza de date cu constrângerile menționate de număr de
noduri si capacitate maxima a unui nod.


Comanda: 
  
    CREATEDB <Db_Name> <No_Nodes> <Max_Capacity>


2) Creare Entitate:


Comanda prin care se creează o entitate cu un factor de replicare si atributele sale care
pot fi de tipul: String, Integer sau Float. Ordinea atributelor din comanda de creare va fi
reflectata si in celelalte comenzi.
Primul atribut va fi întotdeauna considerat cheie primara – după care o instanță a unei
entități va fi identificata unic.


Comanda: 

            CREATE <Entity> <RF> <No_Attributes>
                Atribute_1 Tip_Atribut1
                Atribute_2 Tip_Atribut2
                          ....
                Atribute_n Tip_Atributn
3) Inserare instanță:


Comanda prin care se inserează o instanță a unei entități. Operația înseamnă replicarea
instanței pe nodurile bazei de date in funcție de RF specific entității.
Inserarea într-un nod se va face ordonat după cea mai recentă intrare.


Comanda:

      INSERT <Entity> <Val_Attr1> <Val_Attr2> ... <Val_Attrn>
      
      
4) Ștergere instanță:

Vor fi șterse toate copiile instanței respective de pe toate nodurile în care a fost inserată.


Val_attr1 va reprezenta valoarea cheii primare pentru acea instanță


În cazul în care nu exista va fi printat un mesaj de eroare: „NO INSTANCE TO DELETE”


Comanda:

    DELETE <Entity> <Primary_Key>
    
5) Actualizare instanță:


Actualizează toate copiile instanței respective de pe toate nodurile în care a fost inserata


Comanda:
  
    UPDATE <Entity> <Primary_Key> <Attr1> <New_Val_Attr1> ... <Attrn> <New_Val_Attrn>
6) Regăsire instanță


Returnează o instanță cu toate valorile și nodurile în care se află.
Daca nu exista acea instanță se va returna un mesaj de eroare „NO INSTANCE FOUND”
Formatul pentru printarea valorile de tip float se va fi folosi DecimalFormat cu formatul  #.##


Comanda:

    GET <Entity> <Primary_Key>
Rezultat:

    <Node_1> <Node_2>...<Node_n> <Entity> <Attr1>:<Val_Attr1> ... <Attrn>:<Val_Attrn>
    
7) Print baza de date


Printează întreaga baza de date împreuna cu toate nodurile si datele ce se regăsesc la
acel moment în fiecare nod.
În cazul în care baza de date nu conține nicio instanță a unei entități se va afișa mesajul
de eroare „EMPTY DB”


Comanda:

    SNAPSHOTDB
Rezultat:

    <Nod_1>
    <ENTITY1> <Attr1>:<Val_Attr1> ... <Attrn>:<Val_Attrn>
    <ENTITY2> <Attr1: <Val_Attr1> ... <Attrn>:<Val_Attrn>
    ...
    <Nod_2>
    <ENTITY1> <Attr1>:<Val_Attr1> ... <Attrn>:<Val_Attrn>
    ...

8) Clean-up


Comanda are ca efect ștergerea instanțelor mai vechi de un anumit timestamp
(nanosecunde) dat ca parametru.
De asemenea, trebuie păstrată în continuare ordinea de scriere în noduri după cele mai
recente intrări.
Comanda:

    CLEANUP <DB_NAME> <TIMESTAMP(ns)>
9) Full Database


Implementarea unui mecanism prin care se va scala baza de date in cazul in care este
umpluta la capacitate maxima (nu mai este loc de inserare in niciun nod).
In acest caz, va fi creat un nou nod de fiecare data cu aceeași capacitate  <Max_Capacitaty>  ca a celorlalte noduri.



-------Detalii despre implementare-------


1.Initial am creat o clasa numita Entity care va reprezenta un tip de entitate.
In ea salvez factorul de replicare, numarul de atribute, precum si un arraylist de tipuri de atribute.
In acest arraylist salvez numele atributelor si tipul de care vor fi acestea.


2.Am creat o clasa abastracta Attribute care va reprezenta un tip ,,general" de atribute. Prin urmare, am creat
3 clase care extind clasa Attribute, IntegerAttrbiute, FloatAttribute, StringAttribute care vor reprezenta un
atribut de tip integer,float sau integer.


3.Am creat o clasa numita Instance care va reprezenta o anumita instanta, ce contine in sine numele entitatii
 din care face parte, un int factr de replicare, un long timeStamp ce va reprezenta timpul cand acesta a fost
 creat si un arraylist de atribute in care vom stoca atributele caracteristice acestei instante.
 
4.Am creat o clasa Node care va reprezenta fiecare "nod" a bazei de date. Aceasta contine un TreeSet de
 instante (TreeSet<Instance>), astfel fiecare nod va fi, de fapt, un TreeSet de instante in care vom stoca
  diferite instante. Am folosit TreeSet pentru ca acesta e capabil sa se sorteze automat la fiecare inserare.
  Adica la fiecare inserare, acesta gaseste locul potrivit pentru fiecare element inserat, folosindu-se de 
  un comparator. Acest comparator l-am setat in clasa Instance care implementeaza interfata Comparable, unde 
  am suprascris metoda CompareTo, in asa mod, incat TreeSet-ul va insera sortat dupa timeStamp-ul fiecarei
  instante.
  
5.Am creat clasa DataBase ce va reprezenta o baza de date. Aceasta include in side un HashMap de entitati.
  In acest HashMap inseram toate entitatile create, a carei key va fi numele acestora.De asemenea, mai avem 
  un arraylist de noduri(de obiecte de tip Node) in care inseram un nou nod, in care vor fi adaugate instantele 
  respective.Mai sunt prezente si un string ce va reprezenta numele bazei de date si un int ce va indica
  capacitatea maxima a unui nod.
  Principul de lucru a programului este urmatorul:
  
  
  Initial creeam o entitate in care inseram parametrii specifici, dintre care unul este arraylist-ul de atribute.
  Apoi, cand dorim sa creem o instanta de tipul unei anumite entitati, cautam in HashMap-ul de entitati pe cea potrivita,
  dupa numele(key), apoi parcurgem arraylist-ul de tipuri de atribute a entitatii respective si in acelasi timp, parcurgem 
  si parametrii cititi din fisier caracteristici instantei respective. Parcurgand ambele arraylist-uri, verificam de ce tip
  va fi al n-lea atribut si creeam un atribut de tipul respectiv in care inseram numele si valoarea specificata.Atributul va 
  fi creat cu ajutorul clasei Attribute, precum si a subclaselor acesteia.Spre final, vom obtine un arraylist cu atribute 
  pentru instanta respectiva. Pasul urmator este inserarea acestei instante in noduri conform factorului de replicare.
  Initial verificam daca avem un nod creat si daca e posibila inserarea in acesta, adica are spatiu pentru inca o instanta.
  In caz ca nu avem nod, sau nu e destul spatiu in vreunul, cream alt nod, in care inseram instanta. Repetam acest pas,
  pana cand nu introducem de n -ori, unde n reprezinta factorul de replicare a instantei date.
  Pentru comanda get,delete,update, parcurgem toate instantele din toate nodurile, cautam instanta asupra careia trebuie
   aplicata una din comenzile date si daca gasim, aplicam, in caz ca nu, afisam un mesaj specific.(Implementarea detailata
    in sursa de cod sau javadoc).
  Precum ca baza de date reprezinta un array-list, in caz ca vom vrea sa marim capacitatea acesteia, vom fi capabili sa facem 
  acest lucru.
  Pentru comanda cleanUP, de asemenea, parcurgem toate instantele din toate nodurile, si cautam instantele care au un timestamp
   mai mic decat acel primit ca parametru. Apoi introducem intr-un arraylist adresele tutror instantelor care indeplinesc conditia 
   si dupa, parcurgem acest arraylist si stergem toate instantele din TreeSet.
   Astfel, algoritmul este functional.



