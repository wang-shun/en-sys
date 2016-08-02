package com.chinacreator.c2.sys.sdk;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chinacreator.asp.comp.sys.common.BeanCopierUtil;
import com.chinacreator.asp.comp.sys.core.user.dto.UserDTO;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.sys.sdk.bean.OrgUserModel;
import com.chinacreator.c2.sys.sdk.bean.Orgnization;
import com.chinacreator.c2.sys.sdk.bean.User;
import com.chinacreator.c2.sys.selecttree.cache.OrgUserTreeCache;
import com.chinacreator.c2.sys.selecttree.treenode.OrgUserTreeNode;
import com.chinacreator.c2.web.ds.impl.DynamicTreeNode;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
@Service("sdkUtils")
public class UtilsImpl implements Utils {
    
    @Override
    public List<? extends OrgUserModel> getOrgAndUser(String orgId) {
        List<OrgUserModel> list = new ArrayList<OrgUserModel>();
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
            }).transform(new Function<OrgUserTreeNode, OrgUserModel>() {
                public OrgUserModel apply(OrgUserTreeNode input) {
                    OrgUserModel orgUserModel=null;
                    Object dto=input.getDTO();
                    if ("org".equals(input.getType())) {
                        orgUserModel = new Orgnization();
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
