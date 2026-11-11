# Specyfikacja systemu rezerwacji przestrzeni biurowych

## Nazwa systemu

System rezerwacji przestrzeni biurowych _InvadeSpaces_

## Cel

System umożliwia użytkownikom wyszukiwanie dostępnych przestrzeni biurowych,
tworzenie i obsługę rezerwacji oraz kontrolowanie ich cyklu życia. System
zapobiega nakładaniu się rezerwacji tej samej przestrzeni i zapewnia
jednoznaczną informację o jej dostępności.

## Zakres funkcjonalny

- przeglądanie listy przestrzeni biurowych,
- przeglądanie kalendarza rezerwacji,
- sprawdzanie dostępności przestrzeni dla wybranego terminu,
- tworzenie rezerwacji,
- potwierdzanie rezerwacji,
- anulowanie rezerwacji,
- przeglądanie szczegółów rezerwacji,
- przeglądanie rezerwacji użytkownika,
- wyszukiwanie przestrzeni po nazwie i podstawowych parametrach,
- zarządzanie przestrzeniami przez administratora.

**Poza zakresem:**

- płatności i rozliczenia,
- integracja z systemem kontroli dostępu do budynku,
- automatyczne przydzielanie przestrzeni na podstawie planu zajęć,
- wysyłanie powiadomień e-mail i SMS,
- integracja z zewnętrznymi kalendarzami,
- zarządzanie wyposażeniem i awariami przestrzeni,
- rezerwacje cykliczne.

# Aktorzy i słownik pojęć

## Aktorzy

| Aktor | Odpowiedzialność |
|---|---|
| gość | przeglądanie listy przestrzeni i kalendarza oraz sprawdzanie dostępności |
| użytkownik zarejestrowany | tworzenie, przeglądanie, potwierdzanie i anulowanie własnych rezerwacji |
| administrator | zarządzanie przestrzeniami oraz przeglądanie i obsługa wszystkich rezerwacji |
| system | walidacja danych, kontrola konfliktów terminów, zmiana statusów i zapis danych |

## Słownik

| Pojęcie | Definicja |
|---|---|
| przestrzeń | zasób biurowy możliwy do zarezerwowania, np. sala konferencyjna lub stanowisko |
| sala | przestrzeń przeznaczona do spotkań lub pracy zespołowej |
| rezerwacja | powiązanie użytkownika, przestrzeni i przedziału czasowego |
| użytkownik | osoba korzystająca z systemu; może być gościem, użytkownikiem zarejestrowanym lub administratorem |
| termin | przedział czasu określony datą i godziną rozpoczęcia oraz zakończenia |
| konflikt terminów | sytuacja, w której dwa przedziały dotyczące tej samej przestrzeni zachodzą na siebie |
| dostępność | możliwość utworzenia rezerwacji przestrzeni w podanym terminie |
| właściciel rezerwacji | użytkownik zarejestrowany, który utworzył rezerwację |

## Uszczegółowienie pojęć

### Przestrzeń

Każda przestrzeń ma unikalny identyfikator, nazwę i pojemność. Pojemność jest
dodatnią liczbą całkowitą. Przestrzeń może być rezerwowana tylko wtedy, gdy
jest aktywna.

### Termin

Termin zawiera datę i godzinę rozpoczęcia oraz zakończenia. Początek musi
następować przed końcem. Granica końcowa nie jest częścią rezerwacji, dlatego
rezerwacje `[10:00, 11:00)` i `[11:00, 12:00)` nie powodują konfliktu.

### Stany rezerwacji

Rezerwacja może się znaleźć w jednym z czterech stanów:

- `PENDING` -- utworzona, ale niepotwierdzona,
- `CONFIRMED` -- potwierdzona i zajmująca termin,
- `CANCELED` -- anulowana; nie zajmuje terminu,
- `COMPLETED` -- zakończona po upływie terminu.

Dozwolone przejścia stanów:

```text
PENDING -> CONFIRMED
PENDING -> CANCELED
CONFIRMED -> CANCELED
CONFIRMED -> COMPLETED
```

Stan `CANCELED` i `COMPLETED` jest końcowy. Nie można ponownie potwierdzić
ani anulować rezerwacji w stanie końcowym.

# Reguły biznesowe

| Identyfikator | Nazwa | Opis |
|---|---|---|
| BR-01 | Uprawnienia do rezerwacji | Tylko użytkownik zarejestrowany może utworzyć rezerwację. Gość może sprawdzać dostępność, ale nie może rezerwować przestrzeni. |
| BR-02 | Poprawny termin | Termin musi mieć początek wcześniejszy niż koniec i nie może rozpoczynać się w przeszłości. |
| BR-03 | Istnienie przestrzeni | Rezerwacja może dotyczyć wyłącznie istniejącej i aktywnej przestrzeni. |
| BR-04 | Pojemność | Liczba uczestników nie może być większa od pojemności przestrzeni i musi być dodatnia. |
| BR-05 | Brak konfliktu | Dla jednej przestrzeni nie mogą istnieć nakładające się rezerwacje w stanie `PENDING` lub `CONFIRMED`. |
| BR-06 | Stan początkowy | Nowa rezerwacja jest tworzona w stanie `PENDING`. |
| BR-07 | Potwierdzanie | Rezerwację `PENDING` może potwierdzić jej właściciel lub administrator, jeśli termin nadal jest dostępny. |
| BR-08 | Anulowanie | Rezerwację może anulować jej właściciel lub administrator. Anulowanie zwalnia termin. |
| BR-09 | Ochrona cudzych danych | Użytkownik może przeglądać i zmieniać wyłącznie własne rezerwacje; administrator może obsługiwać wszystkie. |
| BR-10 | Nieodwracalność zakończenia | Rezerwacji `CANCELED` lub `COMPLETED` nie można modyfikować ani przywrócić. |
| BR-11 | Automatyczne zakończenie | Po upływie terminu rezerwacja `CONFIRMED` przechodzi do stanu `COMPLETED`. |
| BR-12 | Spójność operacji | Utworzenie lub zmiana rezerwacji musi być wykonana atomowo; w przypadku błędu dane pozostają bez zmian. |

# Operacje systemu

| Identyfikator | Operacja | Cel | Wejście | Warunki wstępne | Reguły | Wynik pozytywny | Możliwe błędy |
|---|---|---|---|---|---|---|---|
| OP-01 | Utworzenie rezerwacji | Zarezerwowanie przestrzeni | identyfikator przestrzeni, początek, koniec, liczba uczestników | użytkownik jest zalogowany; przestrzeń istnieje i jest aktywna | BR-01--BR-06, BR-12 | rezerwacja zostaje zapisana jako `PENDING` i otrzymuje identyfikator | brak uprawnień, niepoprawny termin, brak przestrzeni, przekroczona pojemność, konflikt |
| OP-02 | Pobranie rezerwacji | Wyświetlenie szczegółów rezerwacji | identyfikator rezerwacji | użytkownik ma dostęp do rezerwacji | BR-09 | system zwraca dane rezerwacji i jej aktualny status | brak rezerwacji, brak uprawnień |
| OP-03 | Potwierdzenie rezerwacji | Zatwierdzenie rezerwacji | identyfikator rezerwacji | rezerwacja istnieje i ma status `PENDING` | BR-05, BR-07, BR-10, BR-12 | status zmienia się na `CONFIRMED` | brak uprawnień, nieaktualny status, konflikt, brak rezerwacji |
| OP-04 | Anulowanie rezerwacji | Zwolnienie zarezerwowanego terminu | identyfikator rezerwacji | rezerwacja istnieje i nie jest zakończona | BR-08--BR-10, BR-12 | status zmienia się na `CANCELED` | brak uprawnień, nieaktualny status, brak rezerwacji |
| OP-05 | Sprawdzenie dostępności | Ustalenie, czy przestrzeń jest wolna | identyfikator przestrzeni, początek, koniec | przestrzeń istnieje; termin jest poprawny | BR-02, BR-03, BR-05 | system zwraca informację o dostępności i ewentualne konflikty | brak przestrzeni, niepoprawny termin |
| OP-06 | Lista rezerwacji użytkownika | Przegląd własnych rezerwacji | identyfikator użytkownika, opcjonalnie zakres dat i status | użytkownik jest zalogowany | BR-09 | system zwraca tylko rezerwacje danego użytkownika | brak uprawnień, niepoprawny zakres |
| OP-07 | Zarządzanie przestrzenią | Dodanie, edycja lub dezaktywacja przestrzeni | dane przestrzeni lub jej identyfikator | aktor ma rolę administratora | BR-03, BR-12 | przestrzeń zostaje utworzona lub zaktualizowana | brak uprawnień, niepoprawna nazwa, niepoprawna pojemność, brak przestrzeni |

# Kryteria akceptacji

| Identyfikator | Scenariusz | G (Given) | W (When) | T (Then) |
|---|---|---|---|---|
| AC-01 | Poprawne utworzenie rezerwacji | użytkownik jest zalogowany, przestrzeń jest aktywna i dostępna, a termin i liczba uczestników są poprawne | użytkownik wysyła żądanie utworzenia rezerwacji | system tworzy rezerwację w stanie `PENDING` i zwraca jej identyfikator |
| AC-02 | Odrzucenie konfliktu | dla przestrzeni istnieje rezerwacja `CONFIRMED` zachodząca na wybrany termin | użytkownik próbuje utworzyć rezerwację | system odrzuca żądanie i nie zapisuje nowej rezerwacji |
| AC-03 | Odrzucenie niepoprawnego terminu | termin kończy się przed rozpoczęciem lub rozpoczyna się w przeszłości | użytkownik wysyła żądanie utworzenia rezerwacji | system zwraca błąd walidacji |
| AC-04 | Potwierdzenie rezerwacji | użytkownik jest właścicielem rezerwacji w stanie `PENDING`, a termin pozostaje wolny | użytkownik potwierdza rezerwację | status rezerwacji zmienia się na `CONFIRMED` |
| AC-05 | Anulowanie rezerwacji | użytkownik jest właścicielem rezerwacji w stanie `CONFIRMED` | użytkownik anuluje rezerwację | status zmienia się na `CANCELED`, a termin staje się dostępny |
| AC-06 | Ochrona cudzej rezerwacji | użytkownik jest zalogowany, ale rezerwacja należy do innego użytkownika | użytkownik próbuje pobrać lub anulować rezerwację | system odmawia dostępu i nie zmienia danych |
| AC-07 | Sprawdzenie wolnego terminu | przestrzeń istnieje, a w podanym terminie nie ma aktywnej rezerwacji | gość sprawdza dostępność | system zwraca `dostępna = true` |
| AC-08 | Sprawdzenie zajętego terminu | przestrzeń ma rezerwację `PENDING` lub `CONFIRMED` nakładającą się na wybrany termin | gość sprawdza dostępność | system zwraca `dostępna = false` |
| AC-09 | Przekroczenie pojemności | przestrzeń ma pojemność 4, a użytkownik podaje 5 uczestników | użytkownik tworzy rezerwację | system odrzuca żądanie z powodu przekroczenia pojemności |
| AC-10 | Automatyczne zakończenie | potwierdzona rezerwacja ma termin zakończenia wcześniejszy niż bieżący czas | system wykonuje aktualizację statusów | rezerwacja otrzymuje status `COMPLETED` i nie jest już dostępna do anulowania |
