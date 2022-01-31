package br.com.alura.loja.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import br.com.alura.loja.modelo.Projeto;

public class ProjetoDAO {

    private static final Map<Long, Projeto> banco = new HashMap<Long, Projeto>();
    private static final AtomicLong contador = new AtomicLong(1);

    static {
        banco.put(1L, new Projeto("Minha loja", 1L, 2014));
        banco.put(2L, new Projeto("Alura", 2L, 2012));
    }

    public void adiciona(Projeto projeto) {
        long id = contador.incrementAndGet();
        projeto.setId(id);
        banco.put(id, projeto);
    }

    public Projeto busca(Long id) {
        return banco.get(id);
    }

    public void remove(long id) {
        banco.remove(id);
    }

}