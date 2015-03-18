package com.rdms.comm.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rdms.base.PageBean;
import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.comm.dao.ProjectDao;
import com.rdms.comm.domain.Project;

@Repository("projectDao")
public class ProjectDaoImpl extends BaseDaoImpl<Project> implements ProjectDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Project> queryJoinPj(String[] pjGrIds) {
		final String hql = "FROM Project AS pj WHERE pj.pjGroup.id IN (:pjGrIds)";
		return this.getSession().createQuery(hql).setParameterList("pjGrIds", pjGrIds).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageBean<Project> queryJoinPjByPage(String[] pjGrIds, int offset,
			int limit, String orderBy, boolean asc) throws Exception {
		final String hql = "FROM Project AS pj WHERE pj.pjGroup.id IN (:pjGrIds) "
				+ (orderBy == null ? "" : "ORDER BY " + orderBy + " "
						+ (asc == true ? "ASC" : "DESC"));
		final String countHql = "SELECT COUNT(*)" + hql;
		Long totalCount = (Long) this.getSession().createQuery(countHql).setParameterList("pjGrIds", pjGrIds).list().get(0);
		PageBean<Project> pageBean = new PageBean<Project>(offset, limit, totalCount);
		List<Project> beanList = this.getSession().createQuery(hql)
													.setParameterList("pjGrIds", pjGrIds)
													.setFirstResult(offset)
													.setMaxResults(offset + limit)
													.list();
		pageBean.setBeanList(beanList);
		return pageBean;
	}

	// 统计每个部门负责的项目个数
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> countEachDeptPj() throws Exception {
//		StringBuffer sb = new StringBuffer();
//		sb.append("SELECT pj.createUser.dept AS dept, COUNT(pj) AS pj_nums ")
//			.append("FROM Project AS pj ")
//			.append("GROUP BY pj.createUser.dept");
//		final String hql = sb.toString();
//		List<Object[]> result = this.getSession().createQuery(hql).list();
		final String sql = "select tb_dept.name as dept, ifnull(tb.pjNums, 0) as pjNums " +
							"from t_comm_dept as tb_dept " +
							"left join " +
							"	(select dept, count(*) as pjNums " +
							"	from t_comm_pj as pj, t_comm_emp as emp " +
							"	where pj.create_user = emp.id " +
							"	group by dept) as tb " +
							"on tb.dept = tb_dept.name";
		List<Object[]> result = this.getSession().createSQLQuery(sql).list();
		return result;
	}

	@Override
	public List<Project> queryPjByDept(String deptName) throws Exception {
		final String hql = "FROM Project AS pj WHERE pj.createUser.dept = ?";
		List<Project> beanList = this.find(hql, deptName);
		return beanList;
	}

}
