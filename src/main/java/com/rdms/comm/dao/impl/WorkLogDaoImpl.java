package com.rdms.comm.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.rdms.base.dao.impl.BaseDaoImpl;
import com.rdms.comm.dao.WorkLogDao;
import com.rdms.comm.domain.WorkLog;

@Repository("workLogDao")
public class WorkLogDaoImpl extends BaseDaoImpl<WorkLog> implements WorkLogDao {

	public List<WorkLog> queryOneWeek(String eid, Date startDate, Date endDate)
			throws Exception {
		final String hql = "FROM WorkLog AS wl WHERE wl.emp.id = ? AND wl.createTime BETWEEN ? AND ?";
		return this.find(hql, eid, startDate, endDate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> countEachDeptEachEmpWorkLog(Date begin, Date end)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT wl.emp.dept, wl.emp.name, COUNT(wl) ")
			.append("FROM WorkLog AS wl ")
			.append("WHERE wl.createTime BETWEEN :begin AND :end ")
			.append("GROUP BY wl.emp.dept, wl.emp");
		final String hql = sb.toString();
		Query query = this.getSession().createQuery(hql);
		query.setParameter("begin", begin);
		query.setParameter("end", end);
		List<Object[]> result = query.list();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> countSpecDeptEachEmpWorkLog(Date begin, Date end, String dept) throws Exception {
//		StringBuffer sb = new StringBuffer();
//		sb.append("SELECT wl.emp.dept, wl.emp.name, COUNT(wl) ")
//			.append("FROM WorkLog AS wl ")
//			.append("WHERE wl.emp.dept = :dept AND wl.createTime BETWEEN :begin AND :end ")
//			.append("GROUP BY wl.emp.dept, wl.emp");
//		final String hql = sb.toString();
//		Query query = this.getSession().createQuery(hql);
//		query.setParameter("dept", dept);
//		query.setParameter("begin", begin);
//		query.setParameter("end", end);
//		List<Object[]> result = query.list();
		final String sql = "select tb_emp.dept, tb_emp.name, ifnull(tb.workLogCount, 0) " +
							"from t_comm_emp as tb_emp " +
							"left join " +
							"(select emp.id as empId, emp.dept as deptName, emp.name as empName, count(wl.id) as workLogCount " +
							"from t_comm_work_log as wl, t_comm_emp as emp " +
							"where wl.eid = emp.id and wl.create_time between :begin and :end " +
							"group by emp.dept, emp.name) as tb " +
							"on tb.empId = tb_emp.id " +
							"where tb_emp.dept = :dept";
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		sqlQuery.setParameter("begin", begin);
		sqlQuery.setParameter("end", end);
		sqlQuery.setParameter("dept", dept);
		List<Object[]> result = sqlQuery.list();
		return result;
	}

}
