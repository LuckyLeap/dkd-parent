package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.Node;
import com.dkd.manage.domain.vo.NodeVo;

/**
 * 点位管理Mapper接口
 */
public interface NodeMapper 
{
    /**
     * 查询点位管理
     * 
     * @param id 点位管理主键
     * @return 点位管理
     */
    Node selectNodeById(Long id);

    /**
     * 查询点位管理列表
     * 
     * @param node 点位管理
     * @return 点位管理集合
     */
    List<Node> selectNodeList(Node node);

    /**
     * 新增点位管理
     * 
     * @param node 点位管理
     * @return 结果
     */
    int insertNode(Node node);

    /**
     * 修改点位管理
     * 
     * @param node 点位管理
     * @return 结果
     */
    int updateNode(Node node);

    /**
     * 删除点位管理
     * 
     * @param id 点位管理主键
     * @return 结果
     */
    int deleteNodeById(Long id);

    /**
     * 批量删除点位管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteNodeByIds(Long[] ids);

    /**
     * 查询点位管理列表
     * @param node 点位管理
     * @return NodeVo集合
     */
    List<NodeVo> selectNodeVoList(Node node);
}