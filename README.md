# WeatherChecker
Zjistěte aktuální počasí ve vaší oblíbené destinaci.

## Pro uživatele

### Jak to funguje?
Stačí znát PSČ vámi zvolené destinace a kód státu, kde se tato destinace nachází.

#### Spuštění serveru
Aby program mohl správně fungovat, musí se jako první spustit serverová část. Spusťte tedy program "RunServer" a vyčkejte, až vypíše hlášku "Server ready...".

#### Spuštění klienta
Po spuštění serveru můžete spustit klientskou aplikaci skrze program WeatherCheckerMain. Řiďte se pokyny, které program vypisuje:
  1. Zadejte kód země, kde se cílová destinace nachází (CZ, SK, US, UK, ...)
  2. Zadejte poštovní směrovací číslo (kód) vybrané destinace
  3. Zobrazil se vám aktuální stav počasí v automaticky vyhodnocené nejbližší pozorované lokalitě
  4. Pokud se nacházíte jinde (chcete vybrat jiné meteorologické stanoviště v blízkosti), stiskněte klávesu "y", pokud jste se získanými informacemi spokojeni, stiskněte "n" - program pak skončí
  5. Pokud jste zvolili, že chcete jiný výběr místa, nabídne vám program několik sledovaných míst v okolí s přiřazenými čísly. Zadáním čísla si zobrazíte aktuální přehled počasí v této lokalitě. Současně také vytvoříte vazbu mezi hledaným PSČ a vybranou lokalitou, takže při příštím dotazu na stejné PSČ se zobrazí informace rovnou pro tuto lokalitu.

## Pro vývojáře

### Užité externí služby
Microsoft Bing

Weather underground

### Vlastní služby
Weather checker

Weather cache


### Propojení služeb
Nástin běhu
