/**
 * These metadata are generated automatically.
 * @type {Object}
 */
module.exports = {
        actorRole: {
        aroId: {
            domain: "DO_IDENTITY",
            required: true
        },
        role: {
            domain: "DO_LABEL",
            required: false
        },
        perId: {
            domain: "DO_IDENTITY",
            required: false
        },
        movId: {
            domain: "DO_IDENTITY",
            required: false
        }
    },
    dummy: {
        value: {
            domain: "DO_IDENTITY",
            required: true
        }
    },
    movie: {
        movId: {
            domain: "DO_IDENTITY",
            required: true
        },
        title: {
            domain: "DO_LABEL",
            required: false
        },
        originalTitle: {
            domain: "DO_LABEL",
            required: false
        },
        synopsis: {
            domain: "DO_TEXT",
            required: false
        },
        shortSynopsis: {
            domain: "DO_TEXT",
            required: false
        },
        keywords: {
            domain: "DO_LABEL",
            required: false
        },
        poster: {
            domain: "DO_HREF",
            required: false
        },
        trailerName: {
            domain: "DO_LABEL",
            required: false
        },
        trailerHref: {
            domain: "DO_HREF",
            required: false
        },
        runtime: {
            domain: "DO_RUNTIME",
            required: false
        },
        movieType: {
            domain: "DO_LABEL_SHORT",
            required: false
        },
        productionYear: {
            domain: "DO_YEAR",
            required: false
        },
        userRating: {
            domain: "DO_RATING",
            required: false
        },
        pressRating: {
            domain: "DO_RATING",
            required: false
        }
    },
    movieCaract: {
        title: {
            domain: "DO_LABEL",
            required: false
        },
        originalTitle: {
            domain: "DO_LABEL",
            required: false
        },
        keywords: {
            domain: "DO_LABEL",
            required: false
        },
        runtime: {
            domain: "DO_RUNTIME",
            required: false
        },
        movieType: {
            domain: "DO_LABEL_SHORT",
            required: false
        },
        productionYear: {
            domain: "DO_YEAR",
            required: false
        },
        movId: {
            domain: "DO_IDENTITY",
            required: true
        }
    },
    movieIndex: {
        movId: {
            domain: "DO_IDENTITY",
            required: true
        },
        title: {
            domain: "DO_LABEL",
            required: false
        },
        titleSortOnly: {
            domain: "DO_TEXT_NOT_TOKENIZED",
            required: false
        },
        originalTitle: {
            domain: "DO_LABEL",
            required: false
        },
        synopsis: {
            domain: "DO_TEXT",
            required: false
        },
        shortSynopsis: {
            domain: "DO_TEXT",
            required: false
        },
        keywords: {
            domain: "DO_LABEL",
            required: false
        },
        poster: {
            domain: "DO_TEXT_NOT_TOKENIZED",
            required: false
        },
        runtime: {
            domain: "DO_RUNTIME",
            required: false
        },
        movieType: {
            domain: "DO_LABEL_SHORT",
            required: false
        },
        productionYear: {
            domain: "DO_YEAR",
            required: false
        },
        userRating: {
            domain: "DO_RATING",
            required: false
        },
        pressRating: {
            domain: "DO_RATING",
            required: false
        },
        actorRoles: {
            domain: "DO_MULTI_VALUES",
            required: false
        },
        writers: {
            domain: "DO_MULTI_VALUES",
            required: false
        },
        camera: {
            domain: "DO_MULTI_VALUES",
            required: false
        },
        producers: {
            domain: "DO_MULTI_VALUES",
            required: false
        },
        directors: {
            domain: "DO_MULTI_VALUES",
            required: false
        }
    },
    movieLink: {
        title: {
            domain: "DO_LABEL",
            required: false
        },
        poster: {
            domain: "DO_HREF",
            required: false
        },
        movieType: {
            domain: "DO_LABEL_SHORT",
            required: false
        },
        productionYear: {
            domain: "DO_YEAR",
            required: false
        },
        movId: {
            domain: "DO_IDENTITY",
            required: true
        }
    },
    movieSynopsis: {
        synopsis: {
            domain: "DO_TEXT",
            required: false
        },
        shortSynopsis: {
            domain: "DO_TEXT",
            required: false
        },
        movId: {
            domain: "DO_IDENTITY",
            required: true
        }
    },
    movieTrailer: {
        trailerName: {
            domain: "DO_LABEL",
            required: false
        },
        trailerHref: {
            domain: "DO_HREF",
            required: false
        },
        movId: {
            domain: "DO_IDENTITY",
            required: true
        }
    },
    person: {
        perId: {
            domain: "DO_IDENTITY",
            required: true
        },
        fullName: {
            domain: "DO_LABEL",
            required: false
        },
        firstName: {
            domain: "DO_LABEL",
            required: false
        },
        lastName: {
            domain: "DO_LABEL",
            required: false
        },
        biography: {
            domain: "DO_TEXT",
            required: false
        },
        shortBiography: {
            domain: "DO_TEXT",
            required: false
        },
        sex: {
            domain: "DO_CODE",
            required: false
        },
        photoHref: {
            domain: "DO_HREF",
            required: false
        },
        birthDate: {
            domain: "DO_DATE",
            required: false
        },
        birthPlace: {
            domain: "DO_LABEL",
            required: false
        },
        activity: {
            domain: "DO_MULTI_VALUES",
            required: false
        }
    },
    personActorRoleLink: {
        fullName: {
            domain: "DO_LABEL",
            required: false
        },
        photoHref: {
            domain: "DO_HREF",
            required: false
        },
        role: {
            domain: "DO_LABEL",
            required: false
        },
        perId: {
            domain: "DO_IDENTITY",
            required: true
        }
    },
    personIndex: {
        perId: {
            domain: "DO_IDENTITY",
            required: true
        },
        fullName: {
            domain: "DO_LABEL",
            required: false
        },
        fullNameSortOnly: {
            domain: "DO_TEXT_NOT_TOKENIZED",
            required: false
        },
        biography: {
            domain: "DO_TEXT",
            required: false
        },
        sex: {
            domain: "DO_LABEL_SHORT",
            required: false
        },
        photoUrl: {
            domain: "DO_HREF",
            required: false
        },
        birthDate: {
            domain: "DO_DATE",
            required: false
        },
        birthPlace: {
            domain: "DO_LABEL",
            required: false
        },
        activity: {
            domain: "DO_MULTI_VALUES",
            required: false
        },
        movies: {
            domain: "DO_MULTI_VALUES",
            required: false
        }
    },
    personLink: {
        fullName: {
            domain: "DO_LABEL",
            required: false
        },
        photoHref: {
            domain: "DO_HREF",
            required: false
        },
        existsInBdd: {
            domain: "DO_ACTIVE",
            required: false
        },
        perId: {
            domain: "DO_IDENTITY",
            required: true
        }
    }
};
