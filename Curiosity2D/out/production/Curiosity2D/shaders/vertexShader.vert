#version 410

in vec2 vertices;
in vec3 colour;
out vec3 c_out;

void main(void){
    gl_Position = vec4(vertices.xy,0.0,1.0);
    c_out = colour;
}