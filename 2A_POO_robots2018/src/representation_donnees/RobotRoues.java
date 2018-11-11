package representation_donnees;

public class RobotRoues extends RobotTerrestre {

    public RobotRoues(Case pos, int vit) {
        super(pos, vit);
    }

    public RobotRoues(Case pos) {
        super(pos, 80);
    }

    @Override
    public double getVitesse(NatureTerrain nt) {
        if(nt == NatureTerrain.HABITAT || nt == NatureTerrain.TERRAIN_LIBRE)
            return super.vitesse;
        else
            return 0;
    }

    @Override
    public void deverserEau(int vol) {
        //rien pour le moment
    }

    @Override
    public void remplirReservoir() {
        //rien pour le moment
    }
}
