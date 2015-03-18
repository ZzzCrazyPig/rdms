package com.rdms.comm.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rdms.base.PageBean;
import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.comm.dao.PjGroupDao;
import com.rdms.comm.domain.PjGroup;

@Repository("pjGroupDao")
public class PjGroupDaoImpl extends BaseDaoImpl<PjGroup> implements PjGroupDao {

	public PageBean<PjGroup> queryFetchPjGrMemsByPage(int offset, int limit,
			Map<String, Object> whereArgs, String orderBy, String asc) throws Exception {
		final String hql = "FROM PjGroup AS pg LEFT JOIN FETCH pg.pjGrMems";
		return this.queryByPage(offset, limit, hql, whereArgs, orderBy, asc);
	}
	
	@Override
	public List<PjGroup> queryAllJoinPjGroup(String eid) throws Exception {
		final String hql = "SELECT pgm.pjGroup FROM PjGrMember AS pgm WHERE pgm.emp.id = ?";
		return this.find(hql, eid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> countEachDeptPjGroup() throws Exception {
//		StringBuffer sb = new StringBuffer();
//		sb.append("SELECT pjGr.createUser.dept, COUNT(pjGr) ")
//			.append("FROM PjGroup AS pjGr ")
//			.append("GROUP BY pjGr.createUser.dept");
//		final String hql = sb.toString();
//		List<Object[]> result = this.getSession().createQuery(hql).list();
		final String sql = "select tb_dept.name as dept, ifnull(tb.pjNums, 0) as pjNums " +
				"from t_comm_dept as tb_dept " +
				"left join " +
				"	(select dept, count(*) as pjNums " +
				"	from t_comm_pj_gr as pjGr, t_comm_emp as emp " +
				"	where pjGr.create_user = emp.id " +
				"	group by dept) as tb " +
				"on tb.dept = tb_dept.name";
		List<Object[]> result = this.getSession().createSQLQuery(sql).list();
		return result;
	}

}
