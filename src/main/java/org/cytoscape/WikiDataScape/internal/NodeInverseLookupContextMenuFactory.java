package org.cytoscape.WikiDataScape.internal;

import org.cytoscape.WikiDataScape.internal.tasks.NodeLookupTask;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JMenuItem;
import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.swing.CyMenuItem;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.TaskManager;

/**
 * Inverse lookup menu item. Queries wikidata for all queryable inverse properties for an item
 *
 * @author gstupp
 */
public class NodeInverseLookupContextMenuFactory implements CyNodeViewContextMenuFactory, ActionListener {

    private CyNetworkView netView;
    private final TaskManager taskManager;


    public NodeInverseLookupContextMenuFactory() {
        CyAppAdapter adapter = CyActivator.getCyAppAdapter();
        this.taskManager = adapter.getTaskManager();
    }

    @Override
    public CyMenuItem createMenuItem(CyNetworkView netView, View<CyNode> nodeView) {
        this.netView = netView;
        JMenuItem root = new JMenuItem("What links here?");
        root.addActionListener(this);
        CyMenuItem cyMenuItem = new CyMenuItem(root, 0);
        return cyMenuItem;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("------- inverse lookup item --------");
        CyNetwork myNet = this.netView.getModel();
        List<CyNode> nodes = CyTableUtil.getNodesInState(myNet, "selected", true);
        NodeLookupTask nodeLookupTask = new NodeLookupTask(nodes, true);
        taskManager.execute(nodeLookupTask.createTaskIterator());

    }
}
