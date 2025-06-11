package me.fortibrine.returnrune.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Message {
    SUCCESS_SET_HOME("success-set-home"),
    SUCCESS_TELEPORT_HOME("success-teleport-home"),
    NO_PERMISSION("no-permission"),
    NOT_PLAYER("not-player"),
    HOME_LIST("home-list"),
    SETHOME_USAGE("sethome-usage"),
    DELHOME_SUCCESS("delhome-success"),
    DELHOME_NOT_FOUND("delhome-not-found"),
    DELHOME_USAGE("delhome-usage"),;

    private final String path;
}
