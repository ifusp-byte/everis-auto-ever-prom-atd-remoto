package br.gov.caixa.siavl.atendimentoremoto.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Component;
import br.gov.caixa.siavl.atendimentoremoto.model.DocumentoCliente;

@Component
@SuppressWarnings({ "squid:S1186"})
public class DocumentoClienteRepositoryImpl implements DocumentoClienteRepository {

	@PersistenceContext
	private EntityManager em;

	@Modifying
	@Transactional
	public void insereDocumentoNotaNative(Long numeroNota, Long cpfCnpj, String tipoPessoa, Date inclusaoDocumento,
			Long tipoDocumento) {

		String query = "INSERT INTO AVL.AVLTB034_DCMNTO_NOTA_NGCCO (NU_NOTA_NEGOCIACAO, NU_CPF_CNPJ_CLIENTE, IC_TIPO_PESSOA, TS_INCLUSAO_DOCUMENTO, NU_TIPO_DOCUMENTO_CLIENTE) SELECT :numeroNota, NU_CPF_CNPJ_CLIENTE, IC_TIPO_PESSOA, TS_INCLUSAO_DOCUMENTO, NU_TIPO_DOCUMENTO_CLIENTE FROM AVL.AVLTB033_DOCUMENTO_CLIENTE WHERE NU_CPF_CNPJ_CLIENTE = :cpfCnpj AND IC_TIPO_PESSOA = :tipoPessoa AND TS_INCLUSAO_DOCUMENTO = :inclusaoDocumento AND NU_TIPO_DOCUMENTO_CLIENTE = :tipoDocumento";
		em.joinTransaction();
		em.createNativeQuery(query).setParameter("numeroNota", numeroNota).setParameter("cpfCnpj", cpfCnpj)
				.setParameter("tipoPessoa", tipoPessoa).setParameter("inclusaoDocumento", inclusaoDocumento)
				.setParameter("tipoDocumento", tipoDocumento).executeUpdate();
	}

	@Override
	public List<DocumentoCliente> findAll() {

		return null;
	}

	@Override
	public List<DocumentoCliente> findAll(Sort sort) {

		return null;
	}

	@Override
	public List<DocumentoCliente> findAllById(Iterable<Long> ids) {

		return null;
	}

	@Override
	public <S extends DocumentoCliente> List<S> saveAll(Iterable<S> entities) {

		return null;
	}

	@Override
	public void flush() {

	}

	@Override
	public <S extends DocumentoCliente> S saveAndFlush(S entity) {

		return null;
	}

	@Override
	public <S extends DocumentoCliente> List<S> saveAllAndFlush(Iterable<S> entities) {

		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<DocumentoCliente> entities) {

	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {

	}

	@Override
	public void deleteAllInBatch() {

	}

	@Override
	public DocumentoCliente getOne(Long id) {

		return null;
	}

	@Override
	public DocumentoCliente getById(Long id) {

		return null;
	}

	@Override
	public DocumentoCliente getReferenceById(Long id) {

		return null;
	}

	@Override
	public <S extends DocumentoCliente> List<S> findAll(Example<S> example) {

		return null;
	}

	@Override
	public <S extends DocumentoCliente> List<S> findAll(Example<S> example, Sort sort) {

		return null;
	}

	@Override
	public Page<DocumentoCliente> findAll(Pageable pageable) {

		return null;
	}

	@Override
	public <S extends DocumentoCliente> S save(S entity) {

		return null;
	}

	@Override
	public Optional<DocumentoCliente> findById(Long id) {

		return Optional.empty();
	}

	@Override
	public boolean existsById(Long id) {

		return false;
	}

	@Override
	public long count() {

		return 0;
	}

	@Override
	public void deleteById(Long id) {

	}

	@Override
	public void delete(DocumentoCliente entity) {

	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {

	}

	@Override
	public void deleteAll(Iterable<? extends DocumentoCliente> entities) {

	}

	@Override
	public void deleteAll() {

	}

	@Override
	public <S extends DocumentoCliente> Optional<S> findOne(Example<S> example) {

		return Optional.empty();
	}

	@Override
	public <S extends DocumentoCliente> Page<S> findAll(Example<S> example, Pageable pageable) {

		return null;
	}

	@Override
	public <S extends DocumentoCliente> long count(Example<S> example) {

		return 0;
	}

	@Override
	public <S extends DocumentoCliente> boolean exists(Example<S> example) {

		return false;
	}

	@Override
	public <S extends DocumentoCliente, R> R findBy(Example<S> example,
			Function<FetchableFluentQuery<S>, R> queryFunction) {

		return null;
	}

	@Override
	public void insereDocumentoNota(Long numeroNota, Long cpfCnpj, String tipoPessoa, Date inclusaoDocumento,
			Long tipoDocumento) {

	}

	@Override
	public Optional<List<Object[]>> numeroNomeDocumento(Long numeroNota) {

		return Optional.empty();
	}

}
