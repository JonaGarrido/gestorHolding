package gestorHolding;

public class obtenerAs implements Visitor {

    
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
        return as.devolvete();
    }

    @Override
    public Administrador visit(Administrador ad) {
        return null;
    }

}
