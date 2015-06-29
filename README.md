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
Aplikace odpovídá klient-server modelu (resp. obsahuje server a základní implementaci klienta). Serverová část využívá webové služby jak vlastní, tak i externí a jejich spojením dává k dispozici prostředky pro výše popsanou funkcionalitu.

### Použité externí služby
Pro získání souřadnic, které odpovídají zadaným informacím, program používá **Locations API** ze sady Microsoft Bing maps REST services. Na dotaz s vyplněným kódem státu a poštovním směrovacím kódem vrátí informace o lokaci vč. zeměpisné šířky a délky, které se používají v dále. Dokumentace k této službě je [zde](https://msdn.microsoft.com/en-us/library/ff701714.aspx).

Informace o počasí jsou získávány z **[Weather underground API](http://www.wunderground.com/weather/api/d/docs?d=index)**. Odtud je použito služby *conditions*, která vrací informace o počasí podle zadaných souřadnic, a služby *geolookup* pro nalezení dalších sledovaných míst v blízkosti určitých souřadnic.

### Použité vlastní služby
Jednou z vlastních implementovaných služeb je **WeatherCache**, která nabízí metody *getWeatherForLocation*, *loadSelection* a *saveSelection*. První zmíněná dostane na vstupu řetězec, resp. JSON reprezentující lokaci (viz implementace třídy *Location*). Druhá metoda načte uloženou lokaci podle kódu státu a PSČ - výsledek vrací opět v JSON řetězci. Poslední metoda *saveSelection* uloží vybranou lokaci (první parametr = lokace jako JSON) pod klíčem složeným z dalších dvou parametrů - PSČ a kód země.

Druhou službou je **WeatherChecker**. Je to služba zastřešující celou funkcionalitu serverové části programu. Pokud chce uživatel získat aktuální stav počasí na zvolené lokalitě (PSČ, kód země), použije metodu *getWeatherByCountryAndZIP* jejíž třetím parametrem je výstupní parametr *cached*, který po provedení této metody obsahuje hodnotu *true* pokud byla lokace načtena z paměti voleb (WeatherCache). Seznam míst, která jsou k dispozici poblíž učeného místa, vrací funkce *getStationsByCountryAndZIP*. O uložení volby do paměti serveru se postará metoda *cacheLocationByCountryAndZIP*.


### Propojení služeb
Základem serverové části je služba **WeatherChecker**. Ta poskytuje agregovanou funkcionalitu zbylých použitých služeb. Tato základní služba využívá externí službu **Locations API** pro zjištění souřadnic místa, službu **WeatherCache** pro načítání informací o počasí, ukládání a načítání vybraných lokací z paměti, a *geolookup* z **Weather underground API**, která je použita pro získání dostupných blízkých stanic.

SLužba **WeatherCache** je závislá na službách z **Weather underground API** a sice konkrétně na službě poskytující aktuální stav počasí (*conditions*).


Tedy závislosti služeb jsou takové:
  - 1 => 2 => 5
  - 1 => 3
  - 1 => 4

Kde relace "a => b" říká, že "a využívá služeb poskytovaných b" a služby jsou očíslovány následovně:
  1. WeatherChecker
  2. WeatherCache
  3. Locations API
  4. geolookup
  5. conditions
