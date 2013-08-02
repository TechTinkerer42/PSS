package org.ets.ereg.common.business.dao;

public interface NativeQuery {
	
	public static final String GET_HIERARCHY_TREE="SELECT EREG_HIER_ID FROM ereg_hier START WITH hier_nam =:hier_nam  CONNECT BY PRIOR ereg_hier_id=parnt_ereg_hier_id";

}
