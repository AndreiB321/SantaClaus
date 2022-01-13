Copyright 2021 Bulica Andrei
321CA

Am creat o baza de date de tip Singleton in sunt stocate toate datele de la
citire, fiind mai usor de transmis date de la o metoda la alta. Pentru obtinerea
datelor am parcurs pas cu pas JSON-ul primit ca
argument. La crearea instantelor am utilizat un Factory pattern pentru fiecare
entitate:
    AnnualChange:
    - Stocheaza schimbarile anuale
    Child:
    - Stocheaza datele despre fiecare copil
    ChildUpdate:
    - Stocheaza datele despre fiecare nou copil / updated
    Gift:
    - Stocheaza datele despre fiecare cadou
Toate aceste clase extind clasa Entity.

Pentru simulare, am parcurs fiecare an in parte(la fiecare pas adaugandu-se in
json-ul final datele), in fiecare runda se verifica mai intai update-urile
copiilor sau daca exista copii noi. Se recalculeaza nicescore-ul fiecaruia
si apoi le sunt atribuite cadouri dupa budgetul calulcat in functie de
nicescore si budgetUnit. In final se actualizeaza datele despre fiecare copil,
si se adauga obiectul Child in lista finala.

Pentru instantiere, se declara o entitate cu argumentul tipului de obiect dorit.
La afisare, am utilizat un JSONArray pentru a stoca datele, iar pentru un output
indentat am utilizat un mapper.

Partea a doua:

Actualizare factory: am eliminat enitatea Child din factory deoarece poate
primi un argument null. Astfel am utilizat un builder pattern pentru a
asigna valorile mandatory, iar niceScoreBonus reprezinta un camp optional.

Am modificat clasele ce apartin de factory pentru a adauga toate datele
necesare. In fiecare an, se calculeaza bugetul copilor in functie de
nicescore, nicescorebonus si elfi.
La parcurgerea copiilor pentru a le putea fi asignate cadourile
am creat un strategy care sorteaza lista de copii in fucntie de "strategy"-ul
din anul respectiv. La asignarea unui cadou se actualizeaza cantitatea
acestuia.

