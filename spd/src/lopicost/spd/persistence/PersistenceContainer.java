package lopicost.spd.persistence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

//import lopicost.config.hibernate.GenericDao;
import lopicost.config.logger.Logger;
//import lopicost.spd.model.Extendedfilter;
import lopicost.spd.utils.*;
//import lopicost.spd.utils.dynamicFilters.DynamicFilters;

import org.hibernate.*;


//public class PersistenceContainer extends GenericDao {
public class PersistenceContainer {
	private Integer start = null;
	private Integer end   = null;
	
	private StringBuffer select = new StringBuffer("");
	private StringBuffer from = new StringBuffer("");
	private StringBuffer where = new StringBuffer("");
	private StringBuffer groupby = new StringBuffer("");
	private StringBuffer having = new StringBuffer("");
	private StringBuffer orderby = new StringBuffer("");
	
	private final static int KEY_SELECT    = 0;
	private final static int KEY_FROM      = 1;
	private final static int KEY_WHERE     = 2;
	private final static int KEY_GROUPBY   = 3;
	private final static int KEY_HAVING   = 4;
	private final static int KEY_ORDERBY   = 5;

	/**
	 * Constructor vacio. Usar cuando ya se disponga de la consulta sql 'troceada'
	 *
	 */
	public PersistenceContainer()
	{
		
	}

	/**
	 * Constructor que tokeniza la sql para transformarla al formato del persistence.
	 * @param pSql Query sql base
	 */
	public PersistenceContainer(String pSql)
	{
		// Pasamos la sql a minúsculas y le quitamos los espacios de los extremos.
		String _origSql= pSql.trim();
		String _lcSql= _origSql.toLowerCase().trim();
		int _nextPosition= 0;
		String _aux= null;
		// Buscamos el select
		if (_lcSql.startsWith("select "))
		{
			_nextPosition= _lcSql.indexOf(" from ");
			_aux= _origSql.substring(0,_nextPosition+1).trim()+" ";
			_lcSql= _lcSql.substring(_nextPosition+1).trim();
			_origSql= _origSql.substring(_nextPosition+1).trim();
			addSelect(_aux);
		}
		// Buscamos el from
		if (_lcSql.startsWith("from "))
		{
			_nextPosition= _lcSql.indexOf(" where ");
			if (_nextPosition==-1)
				_nextPosition= _lcSql.indexOf(" group by ");
			if (_nextPosition==-1)
				_nextPosition= _lcSql.indexOf(" having ");
			if (_nextPosition==-1)
				_nextPosition= _lcSql.indexOf(" order by ");
			if (_nextPosition==-1)
				_nextPosition=_lcSql.length()-1;
			
			_aux= _origSql.substring(0,_nextPosition+1).trim()+" ";
			_lcSql= _lcSql.substring(_nextPosition+1).trim();
			_origSql= _origSql.substring(_nextPosition+1).trim();
			addFrom(_aux);
		}
		// Buscamos el where
		if (_lcSql.startsWith("where "))
		{
			_nextPosition= _lcSql.indexOf(" group by ");
			if (_nextPosition==-1)
				_nextPosition= _lcSql.indexOf(" having ");
			if (_nextPosition==-1)
				_nextPosition= _lcSql.indexOf(" order by ");
			if (_nextPosition==-1)
				_nextPosition=_lcSql.length()-1;
			
			_aux= _origSql.substring(0,_nextPosition+1).trim()+" ";
			_lcSql= _lcSql.substring(_nextPosition+1).trim();
			_origSql= _origSql.substring(_nextPosition+1).trim();
			addWhere(_aux);
		}
		// Buscamos el group by
		if (_lcSql.startsWith("group by "))
		{
			_nextPosition= _lcSql.indexOf(" having ");
			if (_nextPosition==-1)
				_nextPosition= _lcSql.indexOf(" order by ");
			if (_nextPosition==-1)
				_nextPosition=_lcSql.length()-1;
			
			_aux= _origSql.substring(0,_nextPosition+1).trim()+" ";
			_lcSql= _lcSql.substring(_nextPosition+1).trim();
			_origSql= _origSql.substring(_nextPosition+1).trim();
			addGroupBy(_aux);
		}
		// Buscamos el having
		if (_lcSql.startsWith("having "))
		{
			_nextPosition= _lcSql.indexOf(" order by ");
			if (_nextPosition==-1)
				_nextPosition=_lcSql.length()-1;
			
			_aux= _origSql.substring(0,_nextPosition+1).trim()+" ";
			_lcSql= _lcSql.substring(_nextPosition+1).trim();
			_origSql= _origSql.substring(_nextPosition+1).trim();
			addHaving(_aux);
		}
		// Buscamos el order by
		if (_lcSql.startsWith("order by "))
		{
			_nextPosition=_lcSql.length()-1;
			
			_aux= _origSql.substring(0,_nextPosition+1).trim()+" ";
			_lcSql= _lcSql.substring(_nextPosition+1).trim();
			_origSql= _origSql.substring(_nextPosition+1).trim();
			addOrderBy(_aux);
		}


	}

	public void removeSelect ()
	{
		select = null;
		select = new StringBuffer("");
	}
	
	public void removeFrom()
	{
		from = null;
		from = new StringBuffer(""); 	
	}

	public void removeWhere()
	{
		where  = null;
		where  = new StringBuffer(""); 	
	}

	public void removeGroupby()
	{
		groupby  = null;
		groupby  = new StringBuffer(""); 	
	}

	public void removeHaving()
	{
		having  = null;
		having  = new StringBuffer(""); 	
	}
	
	public void removeOrderby()
	{
		orderby  = null;
		orderby  = new StringBuffer(""); 	
	}
	
	public void addSelect(String pSelect)
	{
		addSelect (new StringBuffer(pSelect));
	}
	
	public void addSelect(StringBuffer pSelect)
	{
		select.append(pSelect);
	}
	
	public void addFrom (String pFrom)
	{
		addFrom(new StringBuffer(pFrom));
	}
	
	public void addFrom (StringBuffer pFrom)
	{
		from.append(pFrom);
	}

	public void addWhere (String pWhere)
	{
		String _where= pWhere;
		if (where.length()==0)
		{
			_where= pWhere.trim();
			if (_where.startsWith("and "))
				_where= " where "+_where.substring(4)+" ";
			else if (_where.startsWith("or "))
				_where= " where "+_where.substring(3)+" ";
			else
				_where= " "+_where+" ";
		}
		addWhere(new StringBuffer(_where));
	}
	
	public void addWhere (StringBuffer pWhere)
	{
		where.append(pWhere);
	}
	
	public void addGroupBy (String pGroupBy)
	{
		addGroupBy(new StringBuffer(pGroupBy));
	}
	
	public void addGroupBy (StringBuffer pGroupBy)
	{
		groupby.append(pGroupBy);
	}

	public void addHaving(String pHaving)
	{
		addHaving(new StringBuffer(pHaving));
	}
	
	public void addHaving (StringBuffer pHaving)
	{
		having.append(pHaving);
	}	
	
	public void addOrderBy(String pOrderBy)
	{
		addOrderBy(new StringBuffer(pOrderBy));
	}
	
	public void addOrderBy(StringBuffer pOrderBy)
	{
		orderby.append(pOrderBy);
	}

	public String toHQL ()
	{
		StringBuffer sql = new StringBuffer();
		sql.append(getCommandHQL(KEY_SELECT));
		sql.append(getCommandHQL(KEY_FROM));
		sql.append(getCommandHQL(KEY_WHERE));
		sql.append(getCommandHQL(KEY_GROUPBY));
		sql.append(getCommandHQL(KEY_HAVING));		
		sql.append(getCommandHQL(KEY_ORDERBY));
		//System.out.println(sql);
		return sql.toString();
	}
	
	private StringBuffer getCommandHQL (int piKey)
	{
		StringBuffer sb = new StringBuffer();
		
		switch (piKey) {
			case KEY_SELECT : {
					sb.append(select);	
					break;
				}
			case KEY_FROM :{
					sb.append(from);	
					break;
				}
			case KEY_WHERE :{
					sb.append(where);	
					break;
				}
			case KEY_GROUPBY :{
					sb.append(groupby);	
					break;
				}
			case KEY_HAVING :{
					sb.append(having);	
					break;
				}
			case KEY_ORDERBY :{
					sb.append(orderby);	
					break;
				}			
		}
		
		return sb;
		
	}
	/**
	 * @return
	 */
	public StringBuffer getFrom() {
		return from;
	}

	/**
	 * @return
	 */
	public StringBuffer getGroupby() {
		return groupby;
	}

	/**
	 * @return
	 */
	public StringBuffer getHaving() {
		return groupby;
	}
	
	/**
	 * @return
	 */
	public StringBuffer getOrderby() {
		return orderby;
	}

	/**
	 * @return
	 */
	public StringBuffer getSelect() {
		return select;
	}

	/**
	 * @return
	 */
	public StringBuffer getWhere() {
		return where;
	}

	public void setRange(int pbegin, int pend)
	{
		start = new Integer(pbegin);
		end   = new Integer(pend);
		
	}

}