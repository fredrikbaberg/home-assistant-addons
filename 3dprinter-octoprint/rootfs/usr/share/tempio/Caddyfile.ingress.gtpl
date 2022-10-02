:8099 {
	@ingress {
		remote_ip 172.30.32.2
	}
	{{ if .mjpg_streamer }}
	handle_path /webcam* {
		reverse_proxy 127.0.0.1:8080 {
			flush_interval -1
		}
	}
	{{ end }}
	handle {
		{{ if .recovery }}
		rewrite / /recovery
		{{ end }}
		reverse_proxy @ingress localhost:80 {
			header_up X-Script-Name {{ env "ingress_entry" }}
			header_up -Origin
			header_up Origin 172.30.32.2
			header_up X-Forwarded-For 172.30.32.2
			flush_interval -1
		}
	}
}