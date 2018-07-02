package com.shengwu.cloud.survey.dao.impl;

import com.shengwu.cloud.survey.dao.SurveyUserDao;
import com.shengwu.cloud.survey.model.ConcernListModel;
import com.shengwu.cloud.survey.model.UserBaseModel;
import com.shengwu.cloud.survey.model.UserConcernModel;
import com.shengwu.cloud.survey.model.UserInfoModel;
import com.shengwu.cloud.survey.model.UserUpdateModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author   shengwu。
 * Date:    2017/10/3 0003
 */
@Repository
public class SurveyUserDaoImpl implements SurveyUserDao {
    private static  final  String TBL_USER_LOGIN = "tbl_user_login";
    private static  final  String TBL_USER = "tbl_user";

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final ParameterizedRowMapper<UserBaseModel> userBaseModelMapper = new ParameterizedRowMapper<UserBaseModel>() {
        @Override
        public UserBaseModel mapRow(ResultSet rs, int i) throws SQLException {
            UserBaseModel model = new UserBaseModel();
            model.setUserId(rs.getString("user_id"));
            model.setUserAccount(rs.getString("user_account"));
            model.setUserName(rs.getString("user_name"));
            model.setUserNickName(rs.getString("user_nick_name"));
            model.setPhoneNum(rs.getString("phone_num"));
            model.setMail(rs.getString("mail"));
            model.setSex(rs.getString("sex"));
            model.setDate(rs.getString("date"));
            model.setAge(rs.getString("age"));
            model.setAddress(rs.getString("address"));
            model.setSign(rs.getString("sign"));
            model.setIntroduction(rs.getString("introduction"));
            model.setIconUrl(rs.getString("icon_url"));
            return model;
        }
    };

    @Override
    public String createUser(UserBaseModel userBaseModel) {
        StringBuilder sql = new StringBuilder();
        List<Object> list = new ArrayList<>();
        sql.append("INSERT INTO ").append(TBL_USER_LOGIN).append(" (`user_account`, `user_password`) VALUES (?, ?)");
        list.add(userBaseModel.getUserAccount());
        list.add(userBaseModel.getUserPassword());
        Object[] arg = list.toArray();
        jdbcTemplate.update(sql.toString(), arg);
        sql.delete(0, sql.length());
        sql.append("SELECT user_id AS userId FROM ").append(TBL_USER_LOGIN)
                .append( " WHERE user_account = ? AND user_password = ?");
        return jdbcTemplate.queryForObject(sql.toString(), arg, Integer.class).toString();
    }

    @Override
    public UserBaseModel getUser(String userAccount, String password, String updateTime) {

        List<Object> list = new ArrayList<>();
        String sql = String.format("SELECT a.user_id, a.user_account, a.user_name, a.user_nick_name, a.phone_num,"
                + " a.mail, a.sex, a.date, a.age, a.address, a.sign, a.introduction, a.icon_url, a.create_time, "
                + " a.update_time  FROM %s a JOIN %s  b ON a.user_id = b.user_id  WHERE b.user_account = ? "
                +  " AND b.user_password = ?  AND  b.is_delete = 0 ", TBL_USER, TBL_USER_LOGIN);
        list.add(userAccount);
        list.add(password);
        Object[] arg = list.toArray();

        jdbcTemplate.queryForList(sql, arg);
        jdbcTemplate.query(sql, arg, userBaseModelMapper);
        return jdbcTemplate.query(sql, arg, userBaseModelMapper).get(0);
    }

    @Override
    public Boolean updateUser(UserBaseModel userBaseModel) {
        StringBuilder sql = new StringBuilder();
        List<Object> list = new ArrayList<>();
        sql.append("INSERT INTO `tbl_user` (`user_id`");
        list.add(userBaseModel.getUserId());
        if (StringUtils.isNotBlank(userBaseModel.getUserId())) {
            sql.append(", user_account");
            list.add(userBaseModel.getUserAccount());
        }
        if (StringUtils.isNotBlank(userBaseModel.getUserName())) {
            sql.append(", user_name");
            list.add(userBaseModel.getUserName());
        }
        if (StringUtils.isNotBlank(userBaseModel.getUserNickName())) {
            sql.append(", user_nick_name");
            list.add(userBaseModel.getUserNickName());
        }
        if (StringUtils.isNotBlank(userBaseModel.getPhoneNum())) {
            sql.append(", phone_num");
            list.add(userBaseModel.getPhoneNum());
        }
        if (StringUtils.isNotBlank(userBaseModel.getMail())) {
            sql.append(", mail");
            list.add(userBaseModel.getMail());
        }
        if (StringUtils.isNotBlank(userBaseModel.getSex())) {
            sql.append(", sex");
            list.add(userBaseModel.getSex());
        }
        if (StringUtils.isNotBlank(userBaseModel.getDate())) {
            sql.append(", date");
            list.add(userBaseModel.getDate());
        }
        if (StringUtils.isNotBlank(userBaseModel.getAge())) {
            sql.append(", age");
            list.add(userBaseModel.getAge());
        }
        if (StringUtils.isNotBlank(userBaseModel.getAddress())) {
            sql.append(", address");
            list.add(userBaseModel.getAddress());
        }
        if (StringUtils.isNotBlank(userBaseModel.getSign())) {
            sql.append(", sign");
            list.add(userBaseModel.getSign());
        }
        if (StringUtils.isNotBlank(userBaseModel.getIntroduction())) {
            sql.append(", introduction");
            list.add(userBaseModel.getIntroduction());
        }
        sql.append(" ) VALUES ( ? ");
        for (int i = 0; i < list.size() - 1; i++) {
            sql.append(", ?");
        }
        sql.append(" ) ON DUPLICATE KEY UPDATE  user_name = ?, user_nick_name = ?, phone_num = ?, mail = ?")
                .append(", sex = ?, date = ?, age = ?, address = ?, sign = ?, introduction = ?");
        list.add(userBaseModel.getUserName());
        list.add(userBaseModel.getUserNickName());
        list.add(userBaseModel.getPhoneNum());
        list.add(userBaseModel.getMail());
        list.add(userBaseModel.getSex());
        list.add(userBaseModel.getDate());
        list.add(userBaseModel.getAge());
        list.add(userBaseModel.getAddress());
        list.add(userBaseModel.getSign());
        list.add(userBaseModel.getIntroduction());
        Object[] arg = list.toArray();
        return jdbcTemplate.update(sql.toString(), arg) > 0 ;
    }

    @Override
    public Boolean deleteUser(String userId) {
        String sql = String.format("UPDATE %s a JOIN  %s b ON a.user_id = b.user_id SET a.is_delete = 1 , "
                + " b.is_delete = 1 WHERE  a.user_id = ? ;", TBL_USER_LOGIN, TBL_USER);
        List<Object> list = new ArrayList<>();
        list.add(userId);
        Object[] arg = list.toArray();
        return jdbcTemplate.update(sql, arg) > 1 ;
    }

    @Override
    public Boolean updateUserPassword(UserUpdateModel userUpdateModel) {
        String sql = String.format("UPDATE %s  SET user_password = ? "
                + "  WHERE  user_id = ?   AND is_delete = 0 ;", TBL_USER_LOGIN);
        List<Object> list = new ArrayList<>();
        list.add(userUpdateModel.getUserId());
        if (StringUtils.isNotBlank(userUpdateModel.getPassword())) {
            sql += " AND user_password = ? ";
            list.add(userUpdateModel.getPassword());
        }
        Object[] arg = list.toArray();
        return jdbcTemplate.update(sql, arg) > 1 ;
    }

    @Override
    public Boolean addConcern(String userId, List<String> userConcernIds, Boolean isConcern) {
        return null;
    }

    @Override
    public ConcernListModel getConcernList(String userId) {
        return null;
    }

    @Override
    public UserInfoModel getUserInfo(String userId) {
        return null;
    }

}
