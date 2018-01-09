package com.chinacreator.c2.sys.sdk.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sys.sdk.bean.Organization;
import com.chinacreator.c2.sys.sdk.service.query.OrgUserTreeService;
import com.chinacreator.c2.sys.selecttree.cache.OrgUserTreeCache;
import com.chinacreator.c2.sys.selecttree.treenode.OrgUserTreeNode;
import com.chinacreator.c2.sysmgr.User;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

public class OrgUserTreeServiceImpl implements OrgUserTreeService {

	@Override
    public List<Object> getOrgAndUser(String orgId) {
        List<Object> list = new ArrayList<Object>();
        OrgUserTreeCache cache = ApplicationContextManager.getContext().getBean(OrgUserTreeCache.class);
        List<OrgUserTreeNode> nodes = null;
        if (null == orgId || orgId.trim().equals("") || orgId.trim().equals("0")) {
            nodes = cache.getOrgUserTree().getTree(-1);
        } else {
            nodes = cache.getOrgUserTree().getTree(-1, orgId);
        }
        if (null != nodes && !nodes.isEmpty()) {
            list = Lists.newArrayList(FluentIterable.from(nodes).filter(new Predicate<OrgUserTreeNode>() {
                @Override
                public boolean apply(OrgUserTreeNode input) {
                    if ("org".equals(input.getType())) {
                        return true;
                    } else if ("user".equals(input.getType())) {
                        UserDTO userDTO = (UserDTO) input.getDTO();
                        if (2 == userDTO.getUserIsvalid()) {
                            return true;
                        }
                    }
                    return false;
                }
            }).transform(new Function<OrgUserTreeNode, Object>() {
                public Object apply(OrgUserTreeNode input) {
                	Object orgUserModel=null;
                    Object dto=input.getDTO();
                    if ("org".equals(input.getType())) {
                        orgUserModel = new Organization();
                    } else if ("user".equals(input.getType())) {
                        orgUserModel = new User();
                    }
                    BeanCopierUtil.copy(dto, orgUserModel);
                    return orgUserModel;
                }}));
        }
        return list;
    }

}
