package Q3;

import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.threading.Seat;
import uk.ac.soton.ecs.comp1206.labtestlibrary.interfaces.threading.SeatFactory;
import uk.ac.soton.ecs.comp1206.labtestlibrary.recursion.Tuple;

public class Factory implements SeatFactory {

    @Override
    public Tuple<Class<? extends Seat>, Class<? extends Seat>> getSeats() {
        Class a = SeatX.class;
        Class b = SeatY.class;

        return new Tuple<Class<? extends Seat>, Class<? extends Seat>>(a, b);
    }
}
