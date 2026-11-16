package br.com.unifecaf.estoque.repository;

import br.com.unifecaf.estoque.model.StatusVeiculo;
import br.com.unifecaf.estoque.model.TipoVeiculo;
import br.com.unifecaf.estoque.model.Veiculo;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Monta a consulta dinamica: cada filtro so entra na query se for informado.
 */
public final class VeiculoSpecs {

    private VeiculoSpecs() {
    }

    public static Specification<Veiculo> filtrar(String marca, String modelo,
                                                 BigDecimal precoMin, BigDecimal precoMax,
                                                 Integer anoMin, Integer anoMax,
                                                 StatusVeiculo status, TipoVeiculo tipo) {
        return (root, query, cb) -> {
            List<Predicate> filtros = new ArrayList<>();

            Join<Object, Object> joinModelo = root.join("modelo");
            Join<Object, Object> joinMarca = joinModelo.join("marca");

            if (marca != null && !marca.isBlank()) {
                filtros.add(cb.like(cb.lower(joinMarca.get("nome")), "%" + marca.toLowerCase() + "%"));
            }
            if (modelo != null && !modelo.isBlank()) {
                filtros.add(cb.like(cb.lower(joinModelo.get("nome")), "%" + modelo.toLowerCase() + "%"));
            }
            if (precoMin != null) {
                filtros.add(cb.greaterThanOrEqualTo(root.get("preco"), precoMin));
            }
            if (precoMax != null) {
                filtros.add(cb.lessThanOrEqualTo(root.get("preco"), precoMax));
            }
            if (anoMin != null) {
                filtros.add(cb.greaterThanOrEqualTo(root.get("anoFabricacao"), anoMin));
            }
            if (anoMax != null) {
                filtros.add(cb.lessThanOrEqualTo(root.get("anoFabricacao"), anoMax));
            }
            if (status != null) {
                filtros.add(cb.equal(root.get("status"), status));
            }
            if (tipo != null) {
                filtros.add(cb.equal(root.get("tipo"), tipo));
            }

            return cb.and(filtros.toArray(new Predicate[0]));
        };
    }
}
