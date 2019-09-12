package com.panjikrisnayasa.moviecataloguesubmission2

object TVShowsObject {
    var tvShowsData = arrayOf(
        arrayOf(
            R.drawable.poster_i_am_not_an_animal,
            "I am not an Animal",
            9.5f,
            "Animation, Comedy",
            "30m",
            "BBC Two",
            "I Am Not An Animal is an animated comedy series about the only six talking animals in the world, whose cosseted existence in a vivisection unit is turned upside down when they are liberated by animal rights activists."
        ),
        arrayOf(
            R.drawable.poster_chernobyl,
            "Chernobyl",
            8.9f,
            "Drama",
            "70m, 60m",
            "HBO",
            "A dramatization of the true story of one of the worst man-made catastrophes in history, the catastrophic nuclear accident at Chernobyl. A tale of the brave men and women who sacrificed to save Europe from unimaginable disaster."
        ),
        arrayOf(
            R.drawable.poster_rick_and_morty,
            "Rick and Morty",
            8.5f,
            "Animation, Comedy",
            "22m",
            "Adult Swim",
            "Rick is a mentally-unbalanced but scientifically-gifted old man who has recently reconnected with his family. He spends most of his time involving his young grandson Morty in dangerous, outlandish adventures throughout space and alternate universes. Compounded with Morty's already unstable family life, these events cause Morty much distress at home and school."
        ),
        arrayOf(
            R.drawable.poster_breaking_bad,
            "Breaking Bad",
            8.4f,
            "Drama",
            "45m, 47m",
            "AMC",
            "When Walter White, a New Mexico chemistry teacher, is diagnosed with Stage III cancer and given a prognosis of only two years left to live. He becomes filled with a sense of fearlessness and an unrelenting desire to secure his family's financial future at any cost as he enters the dangerous world of drugs and crime."
        ),
        arrayOf(
            R.drawable.poster_hunter_x_hunter,
            "Hunter X Hunter",
            8.3f,
            "Animation, Comedy",
            "24m",
            "Nippon TV, Netflix",
            "Twelve-year-old Gon Freecss one day discovers that the father he had always been told was dead was alive and well. His Father, Ging, is a Hunter—a member of society's elite with a license to go anywhere or do almost anything. Gon, determined to follow in his father's footsteps, decides to take the Hunter Examination and eventually find his father to prove himself as a Hunter in his own right. But on the way, he learns that there is more to becoming a Hunter than previously thought, and the challenges that he must face are considered the toughest in the world."
        ),
        arrayOf(
            R.drawable.poster_sherlock,
            "Sherlock",
            8.3f,
            "Drama, Mystery",
            "90m",
            "BBC One",
            "A modern update finds the famous sleuth and his doctor partner solving crime in 21st century London."
        ),
        arrayOf(
            R.drawable.poster_planet_earth_ii,
            "Planet Earth II",
            8.3f,
            "Documentary",
            "60m",
            "BBC One",
            "David Attenborough presents a documentary series exploring how animals meet the challenges of surviving in the most iconic habitats on earth."
        ),
        arrayOf(
            R.drawable.poster_avatar_the_last_air_bender,
            "Avatar: The Last Air Bender",
            8.3f,
            "Animation, Fantasy",
            "25m",
            "Nickelodeon",
            "In a war-torn world of elemental magic, a young boy reawakens to undertake a dangerous mystic quest to fulfill his destiny as the Avatar, and bring peace to the world."
        ),
        arrayOf(
            R.drawable.poster_the_twilight_zone,
            "The Twilight Zone",
            8.3f,
            "Drama, Mystery",
            "30m",
            "CBS",
            "A series of unrelated stories containing drama, psychological thriller, fantasy, science fiction, suspense, and/or horror, often concluding with a macabre or unexpected twist."
        ),
        arrayOf(
            R.drawable.poster_stranger_things,
            "Stranger Things",
            8.3f,
            "Drama, Mystery",
            "50m",
            "Netflix",
            "When a young boy vanishes, a small town uncovers a mystery involving secret experiments, terrifying supernatural forces, and one strange little girl."
        )
    )

    fun getListTVShowsData(): ArrayList<TVShowsModel> {
        val list = ArrayList<TVShowsModel>()
        for (tData in tvShowsData) {
            val tvShows = TVShowsModel()
            tvShows.poster = tData[0] as Int
            tvShows.title = tData[1] as String
            tvShows.ratingScore = tData[2] as Float
            tvShows.genre = tData[3] as String
            tvShows.runtime = tData[4] as String
            tvShows.network = tData[5] as String
            tvShows.synopsis = tData[6] as String
            list.add(tvShows)
        }
        return list
    }
}