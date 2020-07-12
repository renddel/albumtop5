# API Routes

```
search artist-> "/getArtist/{id}"
save artist to favorites-> "POST /api"
get list of favorited artists-> "GET /api",
get top 5 list of albums -> "/getTop5Album/{artistId}"
```

# Notes

An application which consumes JSON from iTunes API and produces
information about artist by id. Also there is a possibility to save
artist to favorites with artist JSON body. And finally getting top
5 album of given artist by his amgArtistId.

# Checklist

## Requirements

There should be an API for:
- [x] Artist search
- [x] Adding artist to favorites 
- [x] Get favorite list
- [x] Get top 5 list of artist

- [x] Reasonable amount of tests 
- [x] Solution should be uploaded to version control
- [x] Solution should be built using: Java 11, Spring boot, H2, JPA, REST, JUnit, gitHub
- [x] Exception handling
- [x] Project README.md must be created with instructions 