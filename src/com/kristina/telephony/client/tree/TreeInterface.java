package com.kristina.telephony.client.tree;

import java.util.List;
import java.util.Map;

public interface TreeInterface {
    void showTree(Map<String,List<TreeViewData>> rooms, Long selectedExtensionNumber);

    void setAnswerButtonEnabled(boolean enabled);

    void showError(String message);

    void setHandler(TreeViewHandler handler);

    interface TreeViewHandler {
        void onApparatusSelected(Long extensionNumber);

        void onAnswerClicked();
    }
}
