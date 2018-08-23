filegen
=======

Utility for creating payment/statement files in following formats:

* MT101
* pain.001
* MT940
* camt.053

that is useful for performance testing of payment systems.

It could also watch directory and automatically create confirmations in pain.002 format.

For almost any file generation you need to have accounts CSV file (default accounts.csv) with following columns:

CODE,NAME,EXTID,ADDRESS,CITY,POSTCODE,COUNTRY,ACCOUNT,CURRENCY,BIC

CODE is used to group created files by the channel with -split option.

---

Filip Zawadiak, Tensoli GmbH, fzawadiak@tensoli.com