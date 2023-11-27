package twin.developers.projectmqtt;

public class Animal {
    private String NombreAnimal;
    private String NumeroAnimal;

    public Animal()
    {
        this.NombreAnimal="";
        this.NumeroAnimal="";

    }

    public Animal( String NombreAnimal, String NumeroAnimal )
    {
        this.NombreAnimal=NombreAnimal;
        this.NumeroAnimal=NumeroAnimal;

    }

    public  String getNombreAnimal() {
        return NombreAnimal;
    }

    public void setNombreAnimal(String NombreAnimal) {this.NombreAnimal = NombreAnimal;
    }

    public String getNumeroAnimal() {
        return NumeroAnimal;
    }

    public void setNumeroAnimal(String NumeroAnimal) {
        this.NumeroAnimal = NumeroAnimal;
    }


    @Override
    public String toString() {
        return "Nombre del Animal{" +
                "Nombre='" + NombreAnimal + '\'' +
                ", Numero='" + NumeroAnimal + '\'' +
                '}';
    }

}

