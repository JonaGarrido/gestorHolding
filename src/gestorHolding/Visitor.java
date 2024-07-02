package gestorHolding;

public interface Visitor {
    public Usuario visit(Usuario u);

    public Vendedor visit(Vendedor v);

    public Asesor visit(Asesor as);

    public Administrador visit(Administrador ad);
}