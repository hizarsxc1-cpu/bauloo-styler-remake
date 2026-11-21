package dev.hizarsxc1.bauloo;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

/** Exports original, independently-authored pixel textures as a normal vanilla resource pack. */
public final class StylePackExporter {
    private static final String[] GLOWSTONE = {
        "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAA9klEQVR4nI1TsRGDMAx8exMfa7CFyxQULlKnYwQ6au7CCDSZgQ0YgCXSK0Vs8XYcXYcEjW6s+5fr5dsZOsEAOZhRehbzRrOA/vyzgCwL5iHFQAQphFGtk64mMgA5EQczmO+3hD6NhJQpw8FRWR152Hk+RAFTmOmROWfhJGtk19dz8LC+Qyc5k+5NgJnmzmcIpLOw6o1NlYVOw8LIJuT1YS+PTd2X2Dul0aYmcH/eGPul0ZKD0rQ2V3YGpiV8HrZk5Rtze2aD/qWTjlm+w2YyY/dkhL2x/BnKol4XQD0/jMPSpAC+MD2RcHaKJIen6kiPUzj0ZX+BW/lBQdNs65gsVEyAAAAAElFTkSuQmCC",
        "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAA9UlEQVR4nHVSuRGDMBBcqIGE2J0QuAgGxm0QkqE2GHdB4CIcQ4rLOAeelZfjUCLpZu+0j4qqmwwA7uMDALCMM/w9OgPAcKtR6oWLINZ0qOKaPgHtutvbzKpuMp7bdc93PVfddKi3624lAKTtg9dzOLHwciK2JUEcFPkQrWWcsYwzinbdjVM54Koh8iibmLbPyTR9CQBez+FQb/r0ZxBFdMVIJZYEa9bDrf5F5NioBOJzCkpTX/ZGqiQA1xJURpQKa9lE/8vIjKz8Vz95wAY/RAd5FjlGbaR5BKhm/3rTJxRVN1n043yManLTpyw5m+ibIlMVw/0LddjZA4v0XSMAAAAASUVORK5CYII=",
        "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAABDUlEQVR4nH2TsQ2EMAxFP4hNWIOahuoGuBpliDRkiFNqBkCiTH0TMAgr+Arrg7Gis4QISWw/fxtM/SJTv8geRM5DZA/6+L3zuNc8m/pFOgCYx4ghAN8PMATg/UoAgFwShhCRS0IuqFqzBxE6zWOs33LGZLkkDWAPc9Hs86iZGZT7ALBu8aK9CACNWrNc0uXk91tA8d+vBAbim2s6k8LStESyBMTj2gYnDcvsPHqtnHWLD5EtTXMeIj4jxbNq10gBoLUXvEj2zGa31kz9ImwZjd+2XXS0rQWgGtgMtw73N+v3c7Fu8TlI//pt961el4gerWaeYB6jzkG9hOfAWG0A7Ugu6SawWXih1gX/4/0AvsjviNQrS6kAAAAASUVORK5CYII=",
        "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAA4UlEQVR4nH1TsRGDMBCTvUXWoKfIJDRQ4AmYxC6gccsa9KzkFDn5PrLJN3B/L72EHreVs0BqxgAAGMOCKa4//QP3z7uzBDmkBgCggnJIAIAr7jhwI4cEzyEF55Bw4G7Atqa4wr3Wd7ENEhFgSbVySK2FK+6P3q1KLnC9j6j+e/aqBSWwX1qJejPeMtqNOSTMGDCGpVHGmRnDV4FtslTyU7xuK2fRfHsgVcqqBL24/kVJjO/JpT/2KZlPW14bV9yrP5Vsk+BcjVHj04tk2UOqd2C96gFxkyrg0sdL7KnS3xkAPjhmuJ9iyDo1AAAAAElFTkSuQmCC"
    };

    private StylePackExporter() {}

    public static Path exportGlowstone(int styleIndex) throws IOException {
        int index = Math.floorMod(styleIndex, GLOWSTONE.length);
        Path pack = FabricLoader.getInstance().getGameDir()
                .resolve("resourcepacks").resolve("Bauloo Glowstone " + (index + 1));
        Path texture = pack.resolve("assets/minecraft/textures/block/glowstone.png");
        Files.createDirectories(texture.getParent());
        String metadata = "{\n  \"pack\": {\n    \"pack_format\": 34,\n    \"description\": \"Bauloo Styler Remake - Glowstone style " + (index + 1) + "\"\n  }\n}";
        Files.writeString(pack.resolve("pack.mcmeta"), metadata, StandardCharsets.UTF_8);
        Files.write(texture, Base64.getDecoder().decode(GLOWSTONE[index]));
        return pack;
    }
}
