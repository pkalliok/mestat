# mestat
## helppo väline paikkojen tagaamiseksi

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

### käyttöliittymä
### testaus
### automaatio
