#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;
uniform float Time;

in vec2 texCoord;
in vec2 oneTexel;

out vec4 fragColor;

float rand(vec2 co) {
    return fract(sin(dot(co, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
    vec2 uv = texCoord;
    vec2 fromCenter = uv - 0.5;
    float dist = length(fromCenter);
    vec2 aberDir = dist > 0.001 ? normalize(fromCenter) : vec2(0.0);

    // --- Edge Sharpening (on unmodified samples) ---
    vec3 center = texture(DiffuseSampler, uv).rgb;
    vec3 left   = texture(DiffuseSampler, uv + vec2(-oneTexel.x, 0.0)).rgb;
    vec3 right  = texture(DiffuseSampler, uv + vec2( oneTexel.x, 0.0)).rgb;
    vec3 up     = texture(DiffuseSampler, uv + vec2(0.0,  oneTexel.y)).rgb;
    vec3 down   = texture(DiffuseSampler, uv + vec2(0.0, -oneTexel.y)).rgb;
    vec3 sharpened = clamp(center * 5.0 - left - right - up - down, 0.0, 1.0);
    vec3 color = mix(center, sharpened, 0.3);

    // --- Chromatic Aberration (edge-weighted) ---
    float aberration = dist * 0.007;
    float r = texture(DiffuseSampler, uv + aberDir * aberration).r;
    float b = texture(DiffuseSampler, uv - aberDir * aberration).b;
    color.r = mix(color.r, r, 0.8);
    color.b = mix(color.b, b, 0.8);

    // --- Scanlines ---
    float scanRow = mod(floor(uv.y * InSize.y), 3.0);
    color *= scanRow < 1.0 ? 0.85 : 1.0;

    // --- Film Grain ---
    float grain = (rand(uv + vec2(mod(Time, 100.0))) - 0.5) * 0.055;
    color += grain;

    fragColor = vec4(clamp(color, 0.0, 1.0), 1.0);
}
