package gov.nist.healthcare.cds.domain.wrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Documents {

	private List<Document> docs;
	private Map<String, Document> documentMap;

	public List<Document> getDocs() {
		return docs;
	}

	public Map<String, Document> getDocumentMap() {
		return documentMap;
	}

	public void setDocs(List<Document> docs) {
		this.docs = docs;
		this.documentMap = new HashMap<>();
		for(Document doc: docs) {
			this.documentMap.put(doc.getFileName(), doc);
		}
	}

}
