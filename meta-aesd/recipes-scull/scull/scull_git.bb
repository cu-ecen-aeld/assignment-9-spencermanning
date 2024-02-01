# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

# SRC_URI = "git://git@github.com/cu-ecen-aeld/ldd3.git;protocol=ssh;branch=master"
# SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-spencermanning.git;protocol=ssh;branch=master \
# 			file://init.sh \
# 			"
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-spencermanning.git;protocol=ssh;branch=master \
           file://0001-Remove-all-modules-but-scull-and-misc-modules.patch \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "718cbdf07e082486e008537660e6b9fb4fb07a45"

S = "${WORKDIR}/git"

inherit module

# MODULES_INSTALL_TARGET = "install"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

# Required from the asy7.2 instructions
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "init.sh"

FILES:${PN} += "${sysconfdir}/*"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
	
    # from asy6 or something
	# install -m 0755 ${S}/aesdsocket ${D}${bindir}/
	# install -m 0755 ${S}/aesdsocket-start-stop.sh ${D}${sysconfdir}/init.d/
    
    # install -d ${D}${bindir}
	# install -d ${D}${sysconfdir}/init.d
    # # install -m 0755 ${S}scull_bb.so ${D}/${bindir}

    # install -d ${D}${sysconfdir}/init.d
    # install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    # install -m 0755 ${WORKDIR}/init.sh ${D}${sysconfdir}/init.d
    # install -m 0755 ${S}/misc-modules/faulty.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/

    # Exactly from link
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -m 0755 ${WORKDIR}/init.sh ${D}${sysconfdir}/init.d
    install -m 0755 ${S}/misc-modules/faulty.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
# }
