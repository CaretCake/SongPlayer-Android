/*  Melissa Farrell
    This class handles the songs and their corresponding link. Each is given a UUID as well.
 */

package c.melissa.songplayer

import java.util.UUID

class Song {

    val id: UUID
    var songName: String? = null
    var songLink: String? = null

    init {
        this.id = UUID.randomUUID()
        this.songName = "emptyName"
        this.songLink = "emptyURL"
    }

}

