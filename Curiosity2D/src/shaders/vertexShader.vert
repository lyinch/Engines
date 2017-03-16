#version 410

in vec2 vertices;
out vec2 vertices_out;

void main(void){
    gl_Position = vec4(vertices.xy,0.0,1.0);
    vertices_out = vertices;
}