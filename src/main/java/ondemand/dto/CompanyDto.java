package ondemand.dto;

import org.apache.commons.lang.StringUtils;

public class CompanyDto {
    private String skipA;
    private String cnpj;
    private String cpf;
    private String razaoSocial;
    private String fantasia;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private String tipoContribuinte;
    private String isClienteFinal;
    private String email;
    private String dddCelular;
    private String telefoneCelular;
    private String dddComercial;
    private String telefoneComercial;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String siglaEstado;
    private String cep;
    private String observacoes;
    private String isCliente;
    private String isFornecedor;
    private String isFuncionario;
    private String isRepresentante;
    private String isTransportadora;
    private String userUnitPersistence;
    private String codCidade;
    private String codPais;
    private String codUf;

    public String getCnpj() {
        return cnpj.replace(PONTO, StringUtils.EMPTY).replace(HIFEN, StringUtils.EMPTY).replace(BAR, StringUtils.EMPTY);
    }

    public String getCpf() {
        return cpf.replace(PONTO, StringUtils.EMPTY).replace(HIFEN, StringUtils.EMPTY);
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual.replace(PONTO, StringUtils.EMPTY).replace(HIFEN, StringUtils.EMPTY);
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal.replace(PONTO, StringUtils.EMPTY).replace(HIFEN, StringUtils.EMPTY);
    }

    public String getTelefoneCelular() {
        return telefoneCelular.replace(PONTO, StringUtils.EMPTY).replace(HIFEN, StringUtils.EMPTY);
    }

    public String getTelefoneComercial() {
        return telefoneComercial.replace(PONTO, StringUtils.EMPTY).replace(HIFEN, StringUtils.EMPTY);
    }

    public String getSkipA() {
        return skipA;
    }

    public void setSkipA(String skipA) {
        this.skipA = skipA;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getTipoContribuinte() {
        return tipoContribuinte;
    }

    public void setTipoContribuinte(String tipoContribuinte) {
        this.tipoContribuinte = tipoContribuinte;
    }

    public String getIsClienteFinal() {
        return isClienteFinal;
    }

    public void setIsClienteFinal(String isClienteFinal) {
        this.isClienteFinal = isClienteFinal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDddCelular() {
        return dddCelular;
    }

    public void setDddCelular(String dddCelular) {
        this.dddCelular = dddCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getDddComercial() {
        return dddComercial;
    }

    public void setDddComercial(String dddComercial) {
        this.dddComercial = dddComercial;
    }

    public void setTelefoneComercial(String telefoneComercial) {
        this.telefoneComercial = telefoneComercial;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getIsCliente() {
        return isCliente;
    }

    public void setIsCliente(String isCliente) {
        this.isCliente = isCliente;
    }

    public String getIsFornecedor() {
        return isFornecedor;
    }

    public void setIsFornecedor(String isFornecedor) {
        this.isFornecedor = isFornecedor;
    }

    public String getIsFuncionario() {
        return isFuncionario;
    }

    public void setIsFuncionario(String isFuncionario) {
        this.isFuncionario = isFuncionario;
    }

    public String getIsRepresentante() {
        return isRepresentante;
    }

    public void setIsRepresentante(String isRepresentante) {
        this.isRepresentante = isRepresentante;
    }

    public String getIsTransportadora() {
        return isTransportadora;
    }

    public void setIsTransportadora(String isTransportadora) {
        this.isTransportadora = isTransportadora;
    }

    public String getUserUnitPersistence() {
        return userUnitPersistence;
    }

    public void setUserUnitPersistence(String userUnitPersistence) {
        this.userUnitPersistence = userUnitPersistence;
    }

    public String getCodCidade() {
        return codCidade;
    }

    public void setCodCidade(String codCidade) {
        this.codCidade = codCidade;
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais;
    }

    public String getCodUf() {
        return codUf;
    }

    public void setCodUf(String codUf) {
        this.codUf = codUf;
    }
}
