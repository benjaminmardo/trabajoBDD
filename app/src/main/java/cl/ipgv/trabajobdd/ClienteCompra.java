package cl.ipgv.trabajobdd;

public class ClienteCompra {
    private String id;
    private String nombreCliente;
    private String emailCliente;
    private String telefonoCliente;
    private String producto;

    public ClienteCompra() {}

    public ClienteCompra(String id, String nombreCliente, String emailCliente, String telefonoCliente, String producto) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.emailCliente = emailCliente;
        this.telefonoCliente = telefonoCliente;
        this.producto = producto;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getEmailCliente() { return emailCliente; }
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }

    public String getTelefonoCliente() { return telefonoCliente; }
    public void setTelefonoCliente(String telefonoCliente) { this.telefonoCliente = telefonoCliente; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }
}

