package ondemand.dto;

public class ProductDto {
    private String codInterno;
    private String codBarras;
    private String descricao;
    private String ncm;
    private String unidade;
    private String precoUnitario;
    private String userUnitPersistence;

    public String getPrecoUnitario() {
        return precoUnitario;
    }

    public String getCodInterno() {
        return codInterno;
    }

    public void setCodInterno(String codInterno) {
        this.codInterno = codInterno;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public void setPrecoUnitario(String precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public String getUserUnitPersistence() {
        return userUnitPersistence;
    }

    public void setUserUnitPersistence(String userUnitPersistence) {
        this.userUnitPersistence = userUnitPersistence;
    }
}
