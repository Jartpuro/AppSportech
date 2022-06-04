package co.edu.sena.domain.enumeration;

/**
 * The Offer enumeration.
 */
public enum Offer {
    ATHLETICS("Atletismo"),
    BADMINTON("Badminton"),
    BASKETBALL("Banloncesto"),
    HANDBALL("Balonmano"),
    BASEBALL("Beisbol"),
    BIATHLON("Biatlon"),
    BOXING("Boxeo"),
    BREAKING("Breaking"),
    CYCLING("Cliclismo"),
    BMX("Bmx"),
    EQUESTRIAN("Ecuestre"),
    CLIMBING("Escalada"),
    FENCING("Esgrima"),
    SKI("Esqui"),
    SOCCER("Futbol"),
    GYM("Gimnasia"),
    GOLF("Golf"),
    WEIGHTLIFTING("Halterofilia"),
    HOCKEY("Hockey"),
    JUDO("Judo"),
    KARATE("Karate"),
    STRUGGLE("Lucha"),
    SWIM("Natacion"),
    SKATING("Patinaje"),
    PENTATHLON("Pentatlon"),
    ROWING("Remo"),
    RUGBY("Rugby"),
    SURF("Surf"),
    TAEKWONDO("Taekwondo"),
    TENNIS("Tenis"),
    THREW("Tiro"),
    TRIATHLON("Triatlon"),
    CANDLE("Vela"),
    VOLLEYBALL("Voleibol"),
    WATERPOLO("Waterpolo");

    private final String value;

    Offer(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
