public class testPleasegod {
    public void amethod(){
        System.out.println("fock");
    }
}

final class omg extends testPleasegod{
    @Override
    public void amethod() {
        System.out.println("It workds");
    }

    public static void main(String[] args) {
        omg a = new omg();
        a.amethod();
    }
}

