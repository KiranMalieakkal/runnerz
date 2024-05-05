package dev.kme.runnerz.users;

public record Address(
        String street,
        String suite,
        String city,
        String zipcode,
        Geo geo

) {
}


//          "address": {
//          "street": "Kulas Light",
//          "suite": "Apt. 556",
//          "city": "Gwenborough",
//          "zipcode": "92998-3874",
//          "geo": {
//          "lat": "-37.3159",
//          "lng": "81.1496"
//          }
