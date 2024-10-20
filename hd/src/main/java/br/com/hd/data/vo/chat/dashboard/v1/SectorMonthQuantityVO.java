package br.com.hd.data.vo.chat.dashboard.v1;

import java.io.Serializable;
import java.util.Objects;

public class SectorMonthQuantityVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String sector;
	private Long jan;
	private Long fev;
	private Long mar;
	private Long apr;
	private Long may;
	private Long jun;
	private Long jul;
	private Long aug;
	private Long sep;
	private Long oct;
	private Long nov;
	private Long dece;
	
	public SectorMonthQuantityVO() {}

	public SectorMonthQuantityVO(String sector, Long jan, Long fev, Long mar, Long apr, Long may, Long jun, Long jul,
			Long aug, Long sep, Long oct, Long nov, Long dece) {
		this.sector = sector;
		this.jan = jan;
		this.fev = fev;
		this.mar = mar;
		this.apr = apr;
		this.may = may;
		this.jun = jun;
		this.jul = jul;
		this.aug = aug;
		this.sep = sep;
		this.oct = oct;
		this.nov = nov;
		this.dece = dece;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public Long getJan() {
		return jan;
	}

	public void setJan(Long jan) {
		this.jan = jan;
	}

	public Long getFev() {
		return fev;
	}

	public void setFev(Long fev) {
		this.fev = fev;
	}

	public Long getMar() {
		return mar;
	}

	public void setMar(Long mar) {
		this.mar = mar;
	}

	public Long getApr() {
		return apr;
	}

	public void setApr(Long apr) {
		this.apr = apr;
	}

	public Long getMay() {
		return may;
	}

	public void setMay(Long may) {
		this.may = may;
	}

	public Long getJun() {
		return jun;
	}

	public void setJun(Long jun) {
		this.jun = jun;
	}

	public Long getJul() {
		return jul;
	}

	public void setJul(Long jul) {
		this.jul = jul;
	}

	public Long getAug() {
		return aug;
	}

	public void setAug(Long aug) {
		this.aug = aug;
	}

	public Long getSep() {
		return sep;
	}

	public void setSep(Long sep) {
		this.sep = sep;
	}

	public Long getOct() {
		return oct;
	}

	public void setOct(Long oct) {
		this.oct = oct;
	}

	public Long getNov() {
		return nov;
	}

	public void setNov(Long nov) {
		this.nov = nov;
	}

	public Long getDece() {
		return dece;
	}

	public void setDece(Long dece) {
		this.dece = dece;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apr, aug, dece, fev, jan, jul, jun, mar, may, nov, oct, sector, sep);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SectorMonthQuantityVO other = (SectorMonthQuantityVO) obj;
		return Objects.equals(apr, other.apr) && Objects.equals(aug, other.aug) && Objects.equals(dece, other.dece)
				&& Objects.equals(fev, other.fev) && Objects.equals(jan, other.jan) && Objects.equals(jul, other.jul)
				&& Objects.equals(jun, other.jun) && Objects.equals(mar, other.mar) && Objects.equals(may, other.may)
				&& Objects.equals(nov, other.nov) && Objects.equals(oct, other.oct)
				&& Objects.equals(sector, other.sector) && Objects.equals(sep, other.sep);
	}

}
