package gestorHolding;

public class obtenerAd implements Visitor {

    
    /** 
     * @param u
     * @return Usuario
     */
    @Override
    public Usuario visit(Usuario u) {
        return u.accept(this);
    }

    @Override
    public Vendedor visit(Vendedor v) {
        return null;
    }

    @Override
    public Asesor visit(Asesor as) {
        return null;
    }

    @Override
    public Administrador visit(Administrador ad) {
        return ad.devolvete();
    }
}
