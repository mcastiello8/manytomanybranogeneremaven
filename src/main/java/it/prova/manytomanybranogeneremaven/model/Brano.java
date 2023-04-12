package it.prova.manytomanybranogeneremaven.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "brano")
public class Brano {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "titolo")
	private String titolo;
	@Column(name = "autore")
	private String autore;
	@Column(name = "datapubblicazione")
	private LocalDate dataPubblicazione;

	// campi per le time info del record
	@CreationTimestamp
	@Column(name = "createdatetime")
	private LocalDateTime createDateTime;
	@UpdateTimestamp
	@Column(name = "updatedatetime")
	private LocalDateTime updateDateTime;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "brano_genere", joinColumns = @JoinColumn(name = "brano_id", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "genere_id", referencedColumnName = "ID"))
	private Set<Genere> generi = new HashSet<Genere>();

	public Brano() {
	}

	public Brano(String titolo, String autore, LocalDate dataPubblicazione) {
		this.titolo = titolo;
		this.autore = autore;
		this.dataPubblicazione = dataPubblicazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public LocalDate getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(LocalDate dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public Set<Genere> getGeneri() {
		return generi;
	}

	public void setGeneri(Set<Genere> generi) {
		this.generi = generi;
	}

	@Override
	public String toString() {
		String dataPubblicazioneString = dataPubblicazione != null
				? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dataPubblicazione)
				: " N.D.";

		return "Brano [id=" + id + ", titolo=" + titolo + ", autore=" + autore + ", dataPubblicazione="
				+ dataPubblicazioneString + "]";
	}

	// questi due metodi servirebbero nel caso in cui non vi fossero i Cascade in
	// alto (v. creaECollegaBranoEGenere di BranoServiceImpl)
	public void addToGeneri(Genere genereInstance) {
		this.generi.add(genereInstance);
		genereInstance.getBrani().add(this);
	}

	public void removeFromGeneri(Genere genereInstance) {
		this.generi.remove(genereInstance);
		genereInstance.getBrani().remove(this);
	}
	
	

}
