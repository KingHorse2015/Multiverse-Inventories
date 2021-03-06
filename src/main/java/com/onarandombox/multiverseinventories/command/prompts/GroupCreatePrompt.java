package com.onarandombox.multiverseinventories.command.prompts;

import com.onarandombox.multiverseinventories.api.Inventories;
import com.onarandombox.multiverseinventories.api.profile.WorldGroupProfile;
import com.onarandombox.multiverseinventories.locale.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;

class GroupCreatePrompt extends InventoriesPrompt {

    public GroupCreatePrompt(final Inventories plugin, final CommandSender sender) {
        super(plugin, sender);
    }

    @Override
    public String getPromptText(final ConversationContext conversationContext) {
        return messager.getMessage(Message.GROUP_CREATE_PROMPT);
    }

    @Override
    public Prompt acceptInput(final ConversationContext conversationContext, final String s) {
        final WorldGroupProfile group = plugin.getGroupManager().getGroup(s);
        if (group == null) {
            if (s.isEmpty() || !s.matches("^[a-zA-Z0-9][a-zA-Z0-9_]*$")) {
                messager.normal(Message.GROUP_INVALID_NAME, sender);
                return this;
            }
            final WorldGroupProfile newGroup = plugin.getGroupManager().newEmptyGroup(s);
            return new GroupWorldsPrompt(plugin, sender, newGroup,
                    new GroupSharesPrompt(plugin, sender, newGroup, Prompt.END_OF_CONVERSATION, true), true);
        } else {
            messager.normal(Message.GROUP_EXISTS, sender, s);
        }
        return Prompt.END_OF_CONVERSATION;
    }
}
