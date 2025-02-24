package br.edu.utfpr.td.tsi.postosaude.persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.postosaude.modelo.Medico;

@Component
public class IndexadorMedico {

	public void indexar(Medico medico) {
		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();

		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", String.valueOf(medico.getId()));
		document.addField("cpf", medico.getCpf());
		document.addField("nome", medico.getNome());
		document.addField("idmedico", medico.getIdMedico());
		document.addField("especialidade", medico.getEspecialidade());
		document.addField("tipo", "medico");

		try {
			solr.add(document);
			solr.commit();
			solr.close();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void indexar(List<Medico> medicos) {
		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();

		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Medico medico : medicos) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", String.valueOf(medico.getId()));
			document.addField("cpf", medico.getCpf());
			document.addField("nome", medico.getNome());
			document.addField("idmedico", medico.getIdMedico());
			document.addField("especialidade", medico.getEspecialidade());
			document.addField("tipo", "medico");
			documents.add(document);
		}

		try {
			solr.add(documents);
			solr.commit();
			solr.close();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> procurar(String campo, String termo) {
		List<String> ids = new ArrayList<String>();

		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();

		SolrQuery query = new SolrQuery();
		String queryStr = "";
		
		if (!termo.equals("idmedico")) {
			queryStr = termo + ":" + campo + "*" + " AND tipo:medico";
		}
		else {
			queryStr = termo + ":" + campo + " AND tipo:medico";
		}

		
		query.setQuery(queryStr);
		
		QueryResponse response;
		try {
			response = solr.query(query);
			SolrDocumentList docList = response.getResults();

			for (SolrDocument doc : docList) {
				String id = doc.getFieldValue("id").toString();
				ids.add(id);
			}
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		solr.close();
		return ids;
	}
	
	public void deletar(String id) {
		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();
		SolrQuery query = new SolrQuery();
		query.setQuery("id:" + id + " AND tipo:medico");
		
		try {
			solr.deleteByQuery(query.getQuery());
			solr.commit();
			solr.close();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

}
