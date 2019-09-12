package com.panjikrisnayasa.moviecataloguesubmission2

object MoviesObject {
    var moviesData = arrayOf(
        arrayOf(
            R.drawable.poster_a_star,
            "A Star is Born",
            7.5f,
            "Drama",
            "2h 15m",
            "R",
            "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons."
        ),
        arrayOf(
            R.drawable.poster_aquaman,
            "Aquaman",
            6.8f,
            "Fantasy",
            "2h 24m",
            "PG",
            "Once home to the most advanced civilization on Earth, Atlantis is now an underwater kingdom ruled by the power-hungry King Orm. With a vast army at his disposal, Orm plans to conquer the remaining oceanic people and then the surface world. Standing in his way is Arthur Curry, Orm's half-human, half-Atlantean brother and true heir to the throne."
        ),
        arrayOf(
            R.drawable.poster_avengerinfinity,
            "Avengers: Infinity War",
            8.3f,
            "Fantasy",
            "2h 29m",
            "PG",
            "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain."
        ),
        arrayOf(
            R.drawable.poster_bohemian,
            "Bohemian Rhapsody",
            8.1f,
            "Drama",
            "2h 15m",
            "R",
            "Singer Freddie Mercury, guitarist Brian May, drummer Roger Taylor and bass guitarist John Deacon take the music world by storm when they form the rock 'n' roll band Queen in 1970. Hit songs become instant classics. When Mercury's increasingly wild lifestyle starts to spiral out of control, Queen soon faces its greatest challenge yet – finding a way to keep the band together amid the success and excess."
        ),
        arrayOf(
            R.drawable.poster_spiderman,
            "Spider-Man: Into the Spider-Verse",
            8.4f,
            "Action",
            "1h 57m",
            "PG-13",
            "Miles Morales is juggling his life between being a high school student and being a spider-man. When Wilson \"Kingpin\" Fisk uses a super collider, others from across the Spider-Verse are transported to this dimension."
        ),
        arrayOf(
            R.drawable.poster_venom,
            "Venom",
            6.6f,
            "Science Fiction",
            "1h 52m",
            "R",
            "Investigative journalist Eddie Brock attempts a comeback following a scandal, but accidentally becomes the host of Venom, a violent, super powerful alien symbiote. Soon, he must rely on his newfound powers to protect the world from a shadowy organization looking for a symbiote of their own."
        ),
        arrayOf(
            R.drawable.poster_bumblebee,
            "Bumblebee",
            6.5f,
            "Science Fiction",
            "1h 54m",
            "PG-13",
            "On the run in the year 1987, Bumblebee finds refuge in a junkyard in a small Californian beach town. Charlie, on the cusp of turning 18 and trying to find her place in the world, discovers Bumblebee, battle-scarred and broken. When Charlie revives him, she quickly learns this is no ordinary yellow VW bug."
        ),
        arrayOf(
            R.drawable.poster_deadpool,
            "Deadpool",
            7.6f,
            "Action, Comedy",
            "1h 48m",
            "NC-17",
            "Deadpool tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life."
        ),
        arrayOf(
            R.drawable.poster_birdbox,
            "Bird Box",
            7.0f,
            "Action, Comedy",
            "2h 4m",
            "R",
            "Five years after an ominous unseen presence drives most of society to suicide, a survivor and her two children make a desperate bid to reach safety."
        ),
        arrayOf(
            R.drawable.poster_glass,
            "Glass",
            6.5f,
            "Thriller, Drama",
            "2h 9m",
            "NC-17",
            "In a series of escalating encounters, former security guard David Dunn uses his supernatural abilities to track Kevin Wendell Crumb, a disturbed man who has twenty-four personalities. Meanwhile, the shadowy presence of Elijah Price emerges as an orchestrator who holds secrets critical to both men."
        )
    )

    fun getListMoviesData(): ArrayList<MoviesModel> {
        val list = ArrayList<MoviesModel>()
        for (tData in MoviesObject.moviesData) {
            val movie = MoviesModel()
            movie.poster = tData[0] as Int
            movie.title = tData[1] as String
            movie.ratingScore = tData[2] as Float
            movie.genre = tData[3] as String
            movie.duration = tData[4] as String
            movie.rating = tData[5] as String
            movie.synopsis = tData[6] as String
            list.add(movie)
        }
        return list
    }
}