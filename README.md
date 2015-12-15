# mestat - helppo väline paikkojen tagaamiseksi

Tämä ohjelmisto on Panu Kalliokosken harjoitustyö Solitalla.  Sen
tarkoitus on luoda mahdollisimman yksinkertainen www-käyttöliittymä,
jonka kautta voi luoda ja selata tageja, joilla on sijainti.
Käyttöliittymä tukee proksimiteettihakuja, aihehakuja ja osaa kysyä
päätelaitteilta sijainnin siten, ettei käyttäjän tarvitse itse syöttää
sitä.  Mahdollisesti jossain vaiheessa lisätään myös tuki jollekin
geokoodausrajapinnalle siten, että paikan voi määrittää osoitteen tai
POI:n perusteella.

### ohjelmiston rakenne

mestat on tyypillinen SQL-tietokannan päälle rakennettu sovellus.  Alla
toimiva tietokanta on PostgreSQL valmiiden paikkatieto-ominaisuuksien
vuoksi.  Sovellus toteutetaan itsenäisenä http-palvelinprosessina
Clojuren Ring-frameworkilla.  Kaikki komponentit eriytetään toisistaan
Docker-säilöihin.

### tietomalli

Varsinaisessa domainissaan mestat pitää kirjaa vain kahdesta asiasta:
sijainneista ja niihin liittyvistä tageista.  "Tagi" on mitä tahansa
tekstiä, joka kuvaa sijainnin tiettyä ominaisuutta.  Lisäksi käyttäjillä
on tunnistautumista varten käyttäjänimi ja salaisuus, joista pidetään
vielä erikseen kirjaa.

Tageilla on tekstisisältönsä lisäksi nimiavaruus, joka kertoo, minkä
"tyylisestä" tagista on kyse.  Kunkin käyttäjän lisäämät tagit ovat
hänen omassa nimiavaruudessaan, joka on sama kuin käyttäjän nimi.
"system"-nimiavaruudessa on tageja, joita vain mestat-sovellus itse voi
lisätä ja jotka sisältävät metatietoa sijainnista, kuten tietoa siitä,
miten se on lisätty.

### käyttöliittymä
### testaus

Testauksessa käytetään kahdenlaisia tekniikoita:

* clojuren sisäisiä testejä, jotka käynnistävät ja testaavat
  komponentteja (yksikkö- ja integraatiotestit), nämä tehdään
  clojure.test -kirjastolla
* koko ohjelmistoa ulkopuolelta käyttäviä testejä (funktionaaliset ja
  käyttöönottoprosessin testit), nämä on tällä hetkellä toteutettu shell
  skripteinä (+ palvelun käynnistysautomaatio makefilenä)

### automaatio
